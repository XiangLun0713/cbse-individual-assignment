package org.intellibear.cbseindividualassignment.service;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;

public interface ClinicianService {
    ClinicianDTO getClinicianById(Long clinicianId);

    List<ClinicianDTO> getAllClinicians();

    void addClinician(ClinicianDTO clinicianDTO, Long userId);

    ClinicianDTO updateClinician(Long clinicianId, ClinicianDTO clinicianDTO);

    void deleteClinician(Long clinicianId);

    List<ClinicianDTO> getCliniciansByUserId(Long userId);
}