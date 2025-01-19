package org.intellibear.cbseindividualassignment.mapper;

import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordDTO;
import org.intellibear.cbseindividualassignment.model.entity.MedicalRecord;

public class MedicalRecordMapper {
    public static MedicalRecordDTO toDTO(MedicalRecord medicalRecord) {
        if (medicalRecord == null) return null;
        
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setId(medicalRecord.getId());
        dto.setClinicalSetting(medicalRecord.getClinicalSetting());
        dto.setSpecialty(medicalRecord.getSpecialty());
        dto.setHospital(medicalRecord.getHospital());
        dto.setCondition(medicalRecord.getCondition());
        dto.setDiagnosis(medicalRecord.getDiagnosis());
        dto.setManagementPlan(medicalRecord.getManagementPlan());
        dto.setDiscussion(medicalRecord.getDiscussion());
        dto.setFindings(medicalRecord.getFindings());
        dto.setClinicianId(medicalRecord.getAssignedClinician() != null ? 
            medicalRecord.getAssignedClinician().getId() : null);
        dto.setCreatedAt(medicalRecord.getCreatedAt());
        return dto;
    }

    public static MedicalRecord toEntity(MedicalRecordDTO dto) {
        if (dto == null) return null;
        
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setClinicalSetting(dto.getClinicalSetting());
        medicalRecord.setSpecialty(dto.getSpecialty());
        medicalRecord.setHospital(dto.getHospital());
        medicalRecord.setCondition(dto.getCondition());
        medicalRecord.setDiagnosis(dto.getDiagnosis());
        medicalRecord.setManagementPlan(dto.getManagementPlan());
        medicalRecord.setDiscussion(dto.getDiscussion());
        medicalRecord.setFindings(dto.getFindings());
        return medicalRecord;
    }
}