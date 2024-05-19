package com.example.demo.service;

import com.example.demo.dto.VisitDto;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Visit;
import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.exception.VisitNotFoundException;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.VisitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository, ModelMapper modelMapper,PatientRepository patientRepository) {
        this.visitRepository = visitRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }

    public VisitDto saveVisit(VisitDto visitDto) {
        Visit visit = modelMapper.map(visitDto, Visit.class);
        if (visitDto.getVisitDateTime() == null) {
            visit.setVisitDateTime(LocalDateTime.now());
        }else{
            visit.setVisitDateTime(visitDto.getVisitDateTime());
        }
        if (visitDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(visitDto.getPatientId())
                    .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + visitDto.getPatientId()));
            visit.setPatient(patient);
        }
        Visit savedVisit = visitRepository.save(visit);
        VisitDto savedVisitDto = modelMapper.map(savedVisit, VisitDto.class);
    savedVisitDto.setPatientId(savedVisit.getPatient().getPatientId());
    
    return savedVisitDto;
    }


    public List<VisitDto> getAllVisits() {
        List<Visit> visits = visitRepository.findAll();
        return visits.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public VisitDto getVisitById(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
        return convertToDto(visit);
    }

    public VisitDto updateVisit(Long id, VisitDto updatedVisitDto) {
        Visit existingVisit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
    
        if (updatedVisitDto.getVisitDateTime() == null) {
            existingVisit.setVisitDateTime(LocalDateTime.now());
        } else {
            existingVisit.setVisitDateTime(updatedVisitDto.getVisitDateTime());
        }
    
        if (updatedVisitDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(updatedVisitDto.getPatientId())
                    .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + updatedVisitDto.getPatientId()));
            existingVisit.setPatient(patient);
        } 

        Visit updatedVisit = visitRepository.save(existingVisit);
        VisitDto convertedDto = convertToDto(updatedVisit);
        convertedDto.setPatientId(updatedVisit.getPatient().getPatientId());
    
        return convertedDto;
    }
    
    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new VisitNotFoundException("Visit not found with id: " + id);
        }
        visitRepository.deleteById(id);
    }

    public List<VisitDto> getVisitsByPatientId(Long patientId) {
        List<Visit> visits = visitRepository.findByPatientPatientId(patientId);
        return visits.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }

    private VisitDto convertToDto(Visit visit) {
        return modelMapper.map(visit, VisitDto.class);
    }

}
