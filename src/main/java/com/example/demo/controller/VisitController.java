package com.example.demo.controller;

import com.example.demo.dto.VisitDto;
import com.example.demo.entity.Visit;
import com.example.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<VisitDto> saveVisit(@RequestBody VisitDto visitDto) {
        VisitDto savedVisit = visitService.saveVisit(visitDto);
        return ResponseEntity.ok(savedVisit);
    }

    @GetMapping
    public ResponseEntity<List<VisitDto>> getAllVisits() {
        List<VisitDto> visits = visitService.getAllVisits();
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisitById(@PathVariable Long id) {
        VisitDto visitDto = visitService.getVisitById(id);
        return ResponseEntity.ok(visitDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDto> updateVisit(@PathVariable Long id, @RequestBody VisitDto updatedVisitDto) {
        VisitDto visitDto = visitService.updateVisit(id, updatedVisitDto);
        return ResponseEntity.ok(visitDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitDto>> getVisitsByPatientId(@PathVariable Long patientId) {
        List<VisitDto> visits = visitService.getVisitsByPatientId(patientId);
        return ResponseEntity.ok(visits);
    }
}
