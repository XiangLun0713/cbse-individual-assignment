package org.intellibear.cbseindividualassignment.model.dto;

import java.util.List;
import java.util.Map;

public class MedicalRecordInsightsDTO {
    private Long userId;
    private int totalRecords;
    private List<String> uniqueConditions;
    private List<String> uniqueSpecialties;
    private List<String> uniqueHospitals;
    private Map<String, Integer> conditionFrequency;
    private Map<String, Integer> specialtyFrequency;
    private Map<String, Integer> hospitalFrequency;
    private List<ClinicianDTO> mostConsultedClinicians;
    private Map<String, List<String>> conditionToDiagnoses;
    private Map<String, List<String>> conditionToManagementPlans;
    private String mostFrequentCondition;
    private String mostVisitedHospital;
    private String mostConsultedSpecialty;
    private Map<Integer, Integer> recordsPerMonth; // Month -> Count
    private Map<String, Double> conditionPercentages;

    // Add getters and setters for all fields
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<String> getUniqueConditions() {
        return uniqueConditions;
    }

    public void setUniqueConditions(List<String> uniqueConditions) {
        this.uniqueConditions = uniqueConditions;
    }

    public List<String> getUniqueSpecialties() {
        return uniqueSpecialties;
    }

    public void setUniqueSpecialties(List<String> uniqueSpecialties) {
        this.uniqueSpecialties = uniqueSpecialties;
    }

    public List<String> getUniqueHospitals() {
        return uniqueHospitals;
    }

    public void setUniqueHospitals(List<String> uniqueHospitals) {
        this.uniqueHospitals = uniqueHospitals;
    }

    public Map<String, Integer> getConditionFrequency() {
        return conditionFrequency;
    }

    public void setConditionFrequency(Map<String, Integer> conditionFrequency) {
        this.conditionFrequency = conditionFrequency;
    }

    public Map<String, Integer> getSpecialtyFrequency() {
        return specialtyFrequency;
    }

    public void setSpecialtyFrequency(Map<String, Integer> specialtyFrequency) {
        this.specialtyFrequency = specialtyFrequency;
    }

    public Map<String, Integer> getHospitalFrequency() {
        return hospitalFrequency;
    }

    public void setHospitalFrequency(Map<String, Integer> hospitalFrequency) {
        this.hospitalFrequency = hospitalFrequency;
    }

    public List<ClinicianDTO> getMostConsultedClinicians() {
        return mostConsultedClinicians;
    }

    public void setMostConsultedClinicians(List<ClinicianDTO> mostConsultedClinicians) {
        this.mostConsultedClinicians = mostConsultedClinicians;
    }

    public Map<String, List<String>> getConditionToDiagnoses() {
        return conditionToDiagnoses;
    }

    public void setConditionToDiagnoses(Map<String, List<String>> conditionToDiagnoses) {
        this.conditionToDiagnoses = conditionToDiagnoses;
    }

    public Map<String, List<String>> getConditionToManagementPlans() {
        return conditionToManagementPlans;
    }

    public void setConditionToManagementPlans(Map<String, List<String>> conditionToManagementPlans) {
        this.conditionToManagementPlans = conditionToManagementPlans;
    }

    public String getMostFrequentCondition() {
        return mostFrequentCondition;
    }

    public void setMostFrequentCondition(String mostFrequentCondition) {
        this.mostFrequentCondition = mostFrequentCondition;
    }

    public String getMostVisitedHospital() {
        return mostVisitedHospital;
    }

    public void setMostVisitedHospital(String mostVisitedHospital) {
        this.mostVisitedHospital = mostVisitedHospital;
    }

    public String getMostConsultedSpecialty() {
        return mostConsultedSpecialty;
    }

    public void setMostConsultedSpecialty(String mostConsultedSpecialty) {
        this.mostConsultedSpecialty = mostConsultedSpecialty;
    }

    public Map<Integer, Integer> getRecordsPerMonth() {
        return recordsPerMonth;
    }

    public void setRecordsPerMonth(Map<Integer, Integer> recordsPerMonth) {
        this.recordsPerMonth = recordsPerMonth;
    }

    public Map<String, Double> getConditionPercentages() {
        return conditionPercentages;
    }

    public void setConditionPercentages(Map<String, Double> conditionPercentages) {
        this.conditionPercentages = conditionPercentages;
    }
}