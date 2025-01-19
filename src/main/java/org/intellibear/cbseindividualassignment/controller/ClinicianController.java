package org.intellibear.cbseindividualassignment.controller;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;
import org.intellibear.cbseindividualassignment.service.ClinicianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}/clinicians")
public class ClinicianController {

    private final ClinicianService clinicianService;

    public ClinicianController(ClinicianService clinicianService) {
        this.clinicianService = clinicianService;
    }

    @PostMapping
    public ResponseEntity<ClinicianDTO> createClinician(
            @PathVariable Long userId,
            @RequestBody ClinicianDTO clinicianDTO) {
        ClinicianDTO createdClinician = clinicianService.createClinician(clinicianDTO, userId);
        return new ResponseEntity<>(createdClinician, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicianDTO> getClinician(
            @PathVariable Long userId,
            @PathVariable Long id) {
        ClinicianDTO clinician = clinicianService.getClinicianByIdAndUserId(id, userId);
        return ResponseEntity.ok(clinician);
    }

    @GetMapping
    public ResponseEntity<List<ClinicianDTO>> getAllClinicians(
            @PathVariable Long userId) {
        List<ClinicianDTO> clinicians = clinicianService.getCliniciansByUserId(userId);
        return ResponseEntity.ok(clinicians);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicianDTO> updateClinician(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody ClinicianDTO clinicianDTO) {
        ClinicianDTO updatedClinician = clinicianService.updateClinician(id, userId, clinicianDTO);
        return ResponseEntity.ok(updatedClinician);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinician(
            @PathVariable Long userId,
            @PathVariable Long id) {
        clinicianService.deleteClinician(id, userId);
        return ResponseEntity.noContent().build();
    }
}