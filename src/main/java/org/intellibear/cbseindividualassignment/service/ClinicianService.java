package org.intellibear.cbseindividualassignment.service;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;

public interface ClinicianService {
    ClinicianDTO createClinician(ClinicianDTO clinicianDTO, Long userId);

    ClinicianDTO getClinicianByIdAndUserId(Long id, Long userId);

    List<ClinicianDTO> getCliniciansByUserId(Long userId);

    ClinicianDTO updateClinician(Long id, Long userId, ClinicianDTO clinicianDTO);

    void deleteClinician(Long id, Long userId);
}