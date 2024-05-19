package com.example.demo.controller;

import com.example.demo.dto.PatientDto;
import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patient/")
public class PatientController {

	private final PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping("/get")
	public ResponseEntity<List<PatientDto>> getAllPatients() {

		return ResponseEntity.ok(patientService.getAllPatients());
	}

	@GetMapping("/getPatientById/{id}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
		return ResponseEntity.ok(patientService.getPatientById(id));
	}

	@PostMapping("/add")
	public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
		return ResponseEntity.ok(patientService.savePatient(patient));
	}

	@PutMapping("update/{id}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDto updatedPatient) {
		return ResponseEntity.ok(patientService.updatePatient(id, updatedPatient));
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<Void> removePatient(@PathVariable Long id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/age/{age}")
    public ResponseEntity<List<PatientDto>> getPatientsByAge(@PathVariable int age) {
        return ResponseEntity.ok(patientService.getPatientsByAge(age));
    }

    @GetMapping("/gender/{gender}")
public ResponseEntity<List<PatientDto>> getPatientsByGender(@PathVariable String gender) {
    return ResponseEntity.ok(patientService.getPatientsByGender(gender));
}

    @GetMapping("/disease/{diseaseName}")
    public ResponseEntity<List<PatientDto>> getPatientsByDisease(@PathVariable String diseaseName) {
        return ResponseEntity.ok(patientService.getPatientsByDisease(diseaseName));
    }
}
