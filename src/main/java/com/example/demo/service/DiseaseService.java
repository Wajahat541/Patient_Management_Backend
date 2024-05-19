package com.example.demo.service;

import com.example.demo.dto.DiseaseDto;
import com.example.demo.entity.Disease;
import com.example.demo.entity.Patient;
import com.example.demo.exception.DiseaseNotFoundException;
import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.repository.DiseaseRepository;
import com.example.demo.repository.PatientRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository, ModelMapper modelMapper,PatientRepository patientRepository) {
        this.diseaseRepository = diseaseRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }

    public List<DiseaseDto> getAllDiseases() {
        List<Disease> diseases = diseaseRepository.findAll();
        return diseases.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DiseaseDto getDiseaseById(Long id) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new DiseaseNotFoundException("Disease not found with id: " + id));
        return convertToDto(disease);
    }

    public DiseaseDto saveDisease(DiseaseDto diseaseDto) {
        Disease disease = convertToEntity(diseaseDto);
        if(diseaseDto.getPatientId() != null){
        Patient patient = patientRepository.findById(diseaseDto.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + diseaseDto.getPatientId()));
        disease.setPatient(patient);
        }
        Disease savedDisease = diseaseRepository.save(disease);
        return convertToDto(savedDisease);
    }
    

    public DiseaseDto updateDisease( Long id, DiseaseDto updatedDiseaseDto) {
        Disease existingDisease = diseaseRepository.findById(id)
                .orElseThrow(() -> new DiseaseNotFoundException("Disease not found with id: " + id));
        Disease updatedDisease = convertToEntity(updatedDiseaseDto);
        updatedDisease.setDiseaseId(existingDisease.getDiseaseId());
        if(updatedDiseaseDto.getPatientId() != null){
        Patient patient = patientRepository.findById(updatedDiseaseDto.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + updatedDiseaseDto.getPatientId()));
        updatedDisease.setPatient(patient);
    }
        updatedDisease = diseaseRepository.save(updatedDisease);
        return convertToDto(updatedDisease);
    }
    

    public void deleteDisease(Long id) {
        if (!diseaseRepository.existsById(id)) {
            throw new DiseaseNotFoundException("Disease not found with id: " + id);
        }
        diseaseRepository.deleteById(id);
    }
    
    public List<DiseaseDto> getDiseasesByPatientId(Long patientId) {
        List<Disease> diseases = diseaseRepository.findByPatientPatientId(patientId);
        return diseases.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    
    private DiseaseDto convertToDto(Disease disease) {
        return modelMapper.map(disease, DiseaseDto.class);
    }

    private Disease convertToEntity(DiseaseDto diseaseDto) {
        return modelMapper.map(diseaseDto, Disease.class);
    }
}
