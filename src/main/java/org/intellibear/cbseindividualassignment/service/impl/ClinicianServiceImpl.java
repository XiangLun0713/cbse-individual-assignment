package org.intellibear.cbseindividualassignment.service.impl;

import org.intellibear.cbseindividualassignment.mapper.ClinicianMapper;
import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;
import org.intellibear.cbseindividualassignment.model.entity.Clinician;
import org.intellibear.cbseindividualassignment.model.entity.User;
import org.intellibear.cbseindividualassignment.repository.ClinicianRepo;
import org.intellibear.cbseindividualassignment.repository.UserRepo;
import org.intellibear.cbseindividualassignment.service.ClinicianService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicianServiceImpl implements ClinicianService {

    private final ClinicianRepo clinicianRepo;
    private final UserRepo userRepo;

    public ClinicianServiceImpl(ClinicianRepo clinicianRepo, UserRepo userRepo) {
        this.clinicianRepo = clinicianRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ClinicianDTO getClinicianById(Long clinicianId) {
        Clinician clinician = clinicianRepo.findById(clinicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinician not found with ID: " + clinicianId));
        return ClinicianMapper.toDTO(clinician);
    }

    @Override
    public List<ClinicianDTO> getAllClinicians() {
        return clinicianRepo.findAll().stream()
                .map(ClinicianMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addClinician(ClinicianDTO clinicianDTO, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Clinician clinician = ClinicianMapper.toEntity(clinicianDTO);
        clinician.setCreatedAt(LocalDateTime.now());
        
        // Add clinician to user's list
        user.getClinicians().add(clinician);
        userRepo.save(user);
    }

    @Override
    public ClinicianDTO updateClinician(Long clinicianId, ClinicianDTO clinicianDTO) {
        Clinician clinician = clinicianRepo.findById(clinicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinician not found with ID: " + clinicianId));

        clinician.setName(clinicianDTO.getName());
        clinician.setEmail(clinicianDTO.getEmail());
        clinician.setPhoneNumber(clinicianDTO.getPhoneNumber());
        clinician.setSpecialty(clinicianDTO.getSpecialty());
        clinician.setHospital(clinicianDTO.getHospital());

        Clinician updatedClinician = clinicianRepo.save(clinician);
        return ClinicianMapper.toDTO(updatedClinician);
    }

    @Override
    public void deleteClinician(Long clinicianId) {
        if (!clinicianRepo.existsById(clinicianId)) {
            throw new ResourceNotFoundException("Clinician not found with ID: " + clinicianId);
        }
        clinicianRepo.deleteById(clinicianId);
    }

    @Override
    public List<ClinicianDTO> getCliniciansByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        
        return user.getClinicians().stream()
                .map(ClinicianMapper::toDTO)
                .collect(Collectors.toList());
    }
}