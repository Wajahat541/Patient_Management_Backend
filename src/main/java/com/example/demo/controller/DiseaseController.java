package com.example.demo.controller;

import com.example.demo.dto.DiseaseDto;
import com.example.demo.service.DiseaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping
    public ResponseEntity<List<DiseaseDto>> getAllDiseases() {
        List<DiseaseDto> diseases = diseaseService.getAllDiseases();
        return new ResponseEntity<>(diseases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseDto> getDiseaseById(@PathVariable Long id) {
        DiseaseDto diseaseDto = diseaseService.getDiseaseById(id);
        return new ResponseEntity<>(diseaseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiseaseDto> saveDisease(@Valid @RequestBody DiseaseDto diseaseDto) {
        DiseaseDto savedDiseaseDto = diseaseService.saveDisease(diseaseDto);
        return new ResponseEntity<>(savedDiseaseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseDto> updateDisease(@PathVariable Long id, @Valid @RequestBody DiseaseDto diseaseDto) {
        DiseaseDto updatedDiseaseDto = diseaseService.updateDisease(id, diseaseDto);
        return new ResponseEntity<>(updatedDiseaseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/patient/{patientId}")
    public List<DiseaseDto> getDiseasesByPatientId(@PathVariable Long patientId) {
        return diseaseService.getDiseasesByPatientId(patientId);
    }
}
