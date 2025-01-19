package org.intellibear.cbseindividualassignment.service;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordDTO;

public interface MedicalRecordService {
    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO, Long userId);

    MedicalRecordDTO getMedicalRecordByIdAndUserId(Long id, Long userId);

    List<MedicalRecordDTO> getMedicalRecordsByUserId(Long userId);

    MedicalRecordDTO updateMedicalRecord(Long id, Long userId, MedicalRecordDTO medicalRecordDTO);

    void deleteMedicalRecord(Long id, Long userId);
}