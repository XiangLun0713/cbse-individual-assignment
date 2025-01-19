package org.intellibear.cbseindividualassignment.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinical_setting", nullable = false)
    private String clinicalSetting;

    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Column(name = "hospital", nullable = false)
    private String hospital;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "diagnosis", nullable = true)
    private String diagnosis;

    @Column(name = "management_plan", nullable = true)
    private String managementPlan;

    @Column(name = "discussion", nullable = true)
    private String discussion;

    @Column(name = "findings", nullable = true)
    private String findings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinician_id", nullable = true)
    private Clinician assignedClinician;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClinicalSetting() {
        return clinicalSetting;
    }

    public void setClinicalSetting(String clinicalSetting) {
        this.clinicalSetting = clinicalSetting;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getManagementPlan() {
        return managementPlan;
    }

    public void setManagementPlan(String managementPlan) {
        this.managementPlan = managementPlan;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public Clinician getAssignedClinician() {
        return assignedClinician;
    }

    public void setAssignedClinician(Clinician assignedClinician) {
        this.assignedClinician = assignedClinician;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
