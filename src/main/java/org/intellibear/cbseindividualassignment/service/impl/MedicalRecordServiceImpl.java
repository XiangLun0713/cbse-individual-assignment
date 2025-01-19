package org.intellibear.cbseindividualassignment.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.intellibear.cbseindividualassignment.mapper.ClinicianMapper;
import org.intellibear.cbseindividualassignment.mapper.MedicalRecordMapper;
import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;
import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordDTO;
import org.intellibear.cbseindividualassignment.model.dto.MedicalRecordInsightsDTO;
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

    @Override
    public MedicalRecordInsightsDTO getMedicalRecordInsights(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<MedicalRecord> records = user.getMedicalRecords();
        MedicalRecordInsightsDTO insights = new MedicalRecordInsightsDTO();
        insights.setUserId(userId);
        insights.setTotalRecords(records.size());

        if (records.isEmpty()) {
            return insights;
        }

        // Basic Statistics
        Map<String, Integer> conditionFreq = new HashMap<>();
        Map<String, Integer> specialtyFreq = new HashMap<>();
        Map<String, Integer> hospitalFreq = new HashMap<>();
        Map<String, List<String>> conditionToDiagnoses = new HashMap<>();
        Map<String, List<String>> conditionToManagementPlans = new HashMap<>();
        Map<Clinician, Integer> clinicianFreq = new HashMap<>();
        Map<Integer, Integer> recordsPerMonth = new TreeMap<>();

        for (MedicalRecord record : records) {
            // Count frequencies
            conditionFreq.merge(record.getCondition(), 1, Integer::sum);
            specialtyFreq.merge(record.getSpecialty(), 1, Integer::sum);
            hospitalFreq.merge(record.getHospital(), 1, Integer::sum);

            if (record.getAssignedClinician() != null) {
                clinicianFreq.merge(record.getAssignedClinician(), 1, Integer::sum);
            }

            // Group diagnoses and management plans by condition
            conditionToDiagnoses.computeIfAbsent(record.getCondition(), k -> new ArrayList<>())
                    .add(record.getDiagnosis());
            conditionToManagementPlans.computeIfAbsent(record.getCondition(), k -> new ArrayList<>())
                    .add(record.getManagementPlan());

            // Count records per month
            int month = record.getCreatedAt().getMonthValue();
            recordsPerMonth.merge(month, 1, Integer::sum);
        }

        // Calculate most frequent values
        String mostFrequentCondition = getMostFrequent(conditionFreq);
        String mostVisitedHospital = getMostFrequent(hospitalFreq);
        String mostConsultedSpecialty = getMostFrequent(specialtyFreq);

        // Calculate percentages
        Map<String, Double> conditionPercentages = calculatePercentages(conditionFreq, records.size());

        // Get most consulted clinicians (top 5)
        List<ClinicianDTO> mostConsultedClinicians = clinicianFreq.entrySet().stream()
                .sorted(Map.Entry.<Clinician, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> ClinicianMapper.toDTO(entry.getKey()))
                .collect(Collectors.toList());

        // Set all calculated values
        insights.setUniqueConditions(new ArrayList<>(conditionFreq.keySet()));
        insights.setUniqueSpecialties(new ArrayList<>(specialtyFreq.keySet()));
        insights.setUniqueHospitals(new ArrayList<>(hospitalFreq.keySet()));
        insights.setConditionFrequency(conditionFreq);
        insights.setSpecialtyFrequency(specialtyFreq);
        insights.setHospitalFrequency(hospitalFreq);
        insights.setMostConsultedClinicians(mostConsultedClinicians);
        insights.setConditionToDiagnoses(conditionToDiagnoses);
        insights.setConditionToManagementPlans(conditionToManagementPlans);
        insights.setMostFrequentCondition(mostFrequentCondition);
        insights.setMostVisitedHospital(mostVisitedHospital);
        insights.setMostConsultedSpecialty(mostConsultedSpecialty);
        insights.setRecordsPerMonth(recordsPerMonth);
        insights.setConditionPercentages(conditionPercentages);

        return insights;
    }

    private <T> T getMostFrequent(Map<T, Integer> frequency) {
        return frequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private Map<String, Double> calculatePercentages(Map<String, Integer> frequency, int total) {
        return frequency.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (double) e.getValue() / total * 100));
    }
}