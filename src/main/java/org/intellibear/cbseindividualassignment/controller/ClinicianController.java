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
@RequestMapping("/api/clinicians")
public class ClinicianController {

    private final ClinicianService clinicianService;

    public ClinicianController(ClinicianService clinicianService) {
        this.clinicianService = clinicianService;
    }

    @GetMapping("/{clinicianId}")
    public ResponseEntity<ClinicianDTO> getClinicianById(@PathVariable Long clinicianId) {
        ClinicianDTO clinician = clinicianService.getClinicianById(clinicianId);
        return ResponseEntity.ok(clinician);
    }

    @GetMapping
    public ResponseEntity<List<ClinicianDTO>> getAllClinicians() {
        List<ClinicianDTO> clinicians = clinicianService.getAllClinicians();
        return ResponseEntity.ok(clinicians);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Void> addClinician(@RequestBody ClinicianDTO clinicianDTO, @PathVariable Long userId) {
        clinicianService.addClinician(clinicianDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{clinicianId}")
    public ResponseEntity<ClinicianDTO> updateClinician(@PathVariable Long clinicianId,
            @RequestBody ClinicianDTO clinicianDTO) {
        ClinicianDTO updatedClinician = clinicianService.updateClinician(clinicianId, clinicianDTO);
        return ResponseEntity.ok(updatedClinician);
    }

    @DeleteMapping("/{clinicianId}")
    public ResponseEntity<Void> deleteClinician(@PathVariable Long clinicianId) {
        clinicianService.deleteClinician(clinicianId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClinicianDTO>> getCliniciansByUserId(@PathVariable Long userId) {
        List<ClinicianDTO> clinicians = clinicianService.getCliniciansByUserId(userId);
        return ResponseEntity.ok(clinicians);
    }
}