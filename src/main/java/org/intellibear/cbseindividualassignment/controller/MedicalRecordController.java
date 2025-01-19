package org.intellibear.cbseindividualassignment.controller;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordDTO;
import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordInsightsDTO;
import org.intellibear.cbseindividualassignment.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/users/{userId}/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(
            @PathVariable Long userId,
            @RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO createdRecord = medicalRecordService.createMedicalRecord(medicalRecordDTO, userId);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(
            @PathVariable Long userId,
            @PathVariable Long id) {
        MedicalRecordDTO record = medicalRecordService.getMedicalRecordByIdAndUserId(id, userId);
        return ResponseEntity.ok(record);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords(
            @PathVariable Long userId) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByUserId(userId);
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO updatedRecord = medicalRecordService.updateMedicalRecord(id, userId, medicalRecordDTO);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(
            @PathVariable Long userId,
            @PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/insights")
    public ResponseEntity<MedicalRecordInsightsDTO> getMedicalRecordInsights(
            @PathVariable Long userId) {
        MedicalRecordInsightsDTO insights = medicalRecordService.getMedicalRecordInsights(userId);
        return ResponseEntity.ok(insights);
    }
}