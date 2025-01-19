package org.intellibear.cbseindividualassignment.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.intellibear.cbseindividualassignment.mapper.ClinicianMapper;
import org.intellibear.cbseindividualassignment.model.dto.ClinicianDTO;
import org.intellibear.cbseindividualassignment.model.entity.Clinician;
import org.intellibear.cbseindividualassignment.model.entity.User;
import org.intellibear.cbseindividualassignment.repository.ClinicianRepo;
import org.intellibear.cbseindividualassignment.repository.UserRepo;
import org.intellibear.cbseindividualassignment.service.ClinicianService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClinicianServiceImpl implements ClinicianService {

    private final ClinicianRepo clinicianRepo;
    private final UserRepo userRepo;

    public ClinicianServiceImpl(ClinicianRepo clinicianRepo, UserRepo userRepo) {
        this.clinicianRepo = clinicianRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ClinicianDTO createClinician(ClinicianDTO clinicianDTO, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Clinician clinician = ClinicianMapper.toEntity(clinicianDTO);
        clinician.setCreatedAt(LocalDateTime.now());

        Clinician savedClinician = clinicianRepo.save(clinician);

        List<Clinician> userClinicians = user.getClinicians();
        userClinicians.add(savedClinician);
        user.setClinicians(userClinicians);
        userRepo.save(user);

        return ClinicianMapper.toDTO(savedClinician);
    }

    @Override
    public ClinicianDTO getClinicianByIdAndUserId(Long id, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Clinician clinician = user.getClinicians().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Clinician not found"));

        return ClinicianMapper.toDTO(clinician);
    }

    @Override
    public List<ClinicianDTO> getCliniciansByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getClinicians().stream()
                .map(ClinicianMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClinicianDTO updateClinician(Long id, Long userId, ClinicianDTO clinicianDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Clinician existingClinician = user.getClinicians().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Clinician not found"));

        existingClinician.setName(clinicianDTO.getName());
        existingClinician.setEmail(clinicianDTO.getEmail());
        existingClinician.setPhoneNumber(clinicianDTO.getPhoneNumber());
        existingClinician.setSpecialty(clinicianDTO.getSpecialty());
        existingClinician.setHospital(clinicianDTO.getHospital());

        Clinician updatedClinician = clinicianRepo.save(existingClinician);
        return ClinicianMapper.toDTO(updatedClinician);
    }

    @Override
    public void deleteClinician(Long id, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean removed = user.getClinicians().removeIf(clinician -> clinician.getId().equals(id));
        if (!removed) {
            throw new ResourceNotFoundException("Clinician not found");
        }

        userRepo.save(user);
        clinicianRepo.deleteById(id);
    }
}