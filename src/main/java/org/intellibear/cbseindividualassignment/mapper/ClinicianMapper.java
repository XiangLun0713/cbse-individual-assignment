package org.intellibear.cbseindividualassignment.mapper;

import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;
import org.intellibear.cbseindividualassignment.model.entity.Clinician;

public class ClinicianMapper {
    public static ClinicianDTO toDTO(Clinician clinician) {
        ClinicianDTO dto = new ClinicianDTO();
        dto.setId(clinician.getId());
        dto.setName(clinician.getName());
        dto.setEmail(clinician.getEmail());
        dto.setPhoneNumber(clinician.getPhoneNumber());
        dto.setSpecialty(clinician.getSpecialty());
        dto.setHospital(clinician.getHospital());
        return dto;
    }

    public static Clinician toEntity(ClinicianDTO dto) {
        Clinician clinician = new Clinician();
        clinician.setName(dto.getName());
        clinician.setEmail(dto.getEmail());
        clinician.setPhoneNumber(dto.getPhoneNumber());
        clinician.setSpecialty(dto.getSpecialty());
        clinician.setHospital(dto.getHospital());
        return clinician;
    }
}