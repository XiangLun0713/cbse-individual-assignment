package org.intellibear.cbseindividualassignment.service.impl;

import org.intellibear.cbseindividualassignment.mapper.MedicalRecordMapper;
import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordDTO;
import org.intellibear.cbseindividualassignment.model.entity.Clinician;
import org.intellibear.cbseindividualassignment.model.entity.MedicalRecord;
import org.intellibear.cbseindividualassignment.model.entity.User;
import org.intellibear.cbseindividualassignment.repository.ClinicianRepo;
import org.intellibear.cbseindividualassignment.repository.MedicalRecordRepo;
import org.intellibear.cbseindividualassignment.repository.UserRepo;
import org.intellibear.cbseindividualassignment.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepo medicalRecordRepo;
    private final UserRepo userRepo;
    private final ClinicianRepo clinicianRepo;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordRepo medicalRecordRepo,
                                  UserRepo userRepo,
                                  ClinicianRepo clinicianRepo) {
        this.medicalRecordRepo = medicalRecordRepo;
        this.userRepo = userRepo;
        this.clinicianRepo = clinicianRepo;
    }

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MedicalRecord medicalRecord = MedicalRecordMapper.toEntity(medicalRecordDTO);
        medicalRecord.setCreatedAt(LocalDateTime.now());

        if (medicalRecordDTO.getClinicianId() != null) {
            Clinician clinician = clinicianRepo.findById(medicalRecordDTO.getClinicianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Clinician not found"));
            medicalRecord.setAssignedClinician(clinician);
        }

        MedicalRecord savedRecord = medicalRecordRepo.save(medicalRecord);

        List<MedicalRecord> userRecords = user.getMedicalRecords();
        userRecords.add(savedRecord);
        user.setMedicalRecords(userRecords);
        userRepo.save(user);

        return MedicalRecordMapper.toDTO(savedRecord);
    }

    @Override
    public MedicalRecordDTO getMedicalRecordByIdAndUserId(Long id, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MedicalRecord medicalRecord = user.getMedicalRecords().stream()
                .filter(record -> record.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record not found"));

        return MedicalRecordMapper.toDTO(medicalRecord);
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getMedicalRecords().stream()
                .map(MedicalRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDTO updateMedicalRecord(Long id, Long userId, MedicalRecordDTO medicalRecordDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MedicalRecord existingRecord = user.getMedicalRecords().stream()
                .filter(record -> record.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record not found"));

        existingRecord.setClinicalSetting(medicalRecordDTO.getClinicalSetting());
        existingRecord.setSpecialty(medicalRecordDTO.getSpecialty());
        existingRecord.setHospital(medicalRecordDTO.getHospital());
        existingRecord.setCondition(medicalRecordDTO.getCondition());
        existingRecord.setDiagnosis(medicalRecordDTO.getDiagnosis());
        existingRecord.setManagementPlan(medicalRecordDTO.getManagementPlan());
        existingRecord.setDiscussion(medicalRecordDTO.getDiscussion());
        existingRecord.setFindings(medicalRecordDTO.getFindings());

        if (medicalRecordDTO.getClinicianId() != null) {
            Clinician clinician = clinicianRepo.findById(medicalRecordDTO.getClinicianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Clinician not found"));
            existingRecord.setAssignedClinician(clinician);
        }

        MedicalRecord updatedRecord = medicalRecordRepo.save(existingRecord);
        return MedicalRecordMapper.toDTO(updatedRecord);
    }

    @Override
    public void deleteMedicalRecord(Long id, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean removed = user.getMedicalRecords().removeIf(record -> record.getId().equals(id));
        if (!removed) {
            throw new ResourceNotFoundException("Medical Record not found");
        }

        userRepo.save(user);
        medicalRecordRepo.deleteById(id);
    }
}