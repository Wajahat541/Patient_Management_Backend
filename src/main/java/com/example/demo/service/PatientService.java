package com.example.demo.service;

import com.example.demo.dto.DiseaseDto;
import com.example.demo.dto.PatientDto;
import com.example.demo.dto.VisitDto;
import com.example.demo.entity.Patient;
import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.repository.PatientRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

	private final PatientRepository patientRepository;
	
	private final ModelMapper modelMapper;

	public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper; // Initialize ModelMapper
    }


	public List<PatientDto> getAllPatients() throws PatientNotFoundException {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            throw new PatientNotFoundException("No patients found");
        }
        return patients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PatientDto getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
    }


    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
	public void deletePatient(Long id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
		patientRepository.delete(patient);
	}

public PatientDto updatePatient(Long id, PatientDto updatedPatientDto) {
                Patient existingPatient = patientRepository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
                existingPatient.setName(updatedPatientDto.getName());
                existingPatient.setAge(updatedPatientDto.getAge());
                existingPatient.setGender(updatedPatientDto.getGender());
                existingPatient.setPhoneNumber(updatedPatientDto.getPhoneNumber());
                existingPatient.setAddress(updatedPatientDto.getAddress());
                Patient updatedPatient = patientRepository.save(existingPatient);
                return convertToDto(updatedPatient);
            }

            public List<PatientDto> getPatientsByAge(int age) {
                List<Patient> patients = patientRepository.findByAge(age);
                if (patients.isEmpty()) {
                        throw new PatientNotFoundException("No patients found with age:" + age);
                }
                return patients.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            }
            
            public List<PatientDto> getPatientsByGender( String gender) {
                List<Patient> patients = patientRepository.findByGender(gender);
                if (patients.isEmpty()) {
                        throw new PatientNotFoundException("No patients found with gender:" + gender);
                }
                return patients.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            }
            
            public List<PatientDto> getPatientsByDisease(String diseaseName) {
                List<Patient> patients = patientRepository.findByDisease(diseaseName);
                if (patients.isEmpty()) {
                        throw new PatientNotFoundException("No patients found with Disease:" + diseaseName);
                }
                return patients.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            }
            
            
    private PatientDto convertToDto(Patient patient) {
        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        List<VisitDto> visitDtos = patient.getVisits().stream()
                .map(visit -> modelMapper.map(visit, VisitDto.class))
                .collect(Collectors.toList());
        patientDto.setVisits(visitDtos);
		List<DiseaseDto> diseaseDtos = patient.getDiseases().stream()
                .map(disease -> modelMapper.map(disease, DiseaseDto.class))
                .collect(Collectors.toList());
        patientDto.setDiseases(diseaseDtos);
        return patientDto;
    }
}
