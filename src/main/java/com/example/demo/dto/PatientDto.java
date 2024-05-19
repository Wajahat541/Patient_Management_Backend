package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.example.demo.entity.Disease;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto implements Serializable {
	private Long patientId;
    private String name;
    private int age;
    private String gender;
    private String phoneNumber;
    private String address;
    private List<VisitDto> visits;
	private List<DiseaseDto> diseases;
}
