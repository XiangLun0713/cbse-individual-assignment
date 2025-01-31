package org.intellibear.cbseindividualassignment.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.intellibear.cbseindividualassignment.mapper.ResourceMapper;
import org.intellibear.cbseindividualassignment.model.dto.ResourceDTO;
import org.intellibear.cbseindividualassignment.model.entity.MedicalRecord;
import org.intellibear.cbseindividualassignment.model.entity.Resource;
import org.intellibear.cbseindividualassignment.model.entity.User;
import org.intellibear.cbseindividualassignment.repository.ResourceRepo;
import org.intellibear.cbseindividualassignment.repository.UserRepo;
import org.intellibear.cbseindividualassignment.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepo resourceRepo;
    private final UserRepo userRepo;

    @Autowired
    public ResourceServiceImpl(ResourceRepo resourceRepo, UserRepo userRepo) {
        this.resourceRepo = resourceRepo;
        this.userRepo = userRepo;

    }

    @Override
    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        Resource resource = ResourceMapper.toEntity(resourceDTO);
        resource.setCreatedAt(LocalDateTime.now());
        Resource savedResource = resourceRepo.save(resource);
        return ResourceMapper.toDTO(savedResource);
    }

    @Override
    public ResourceDTO getResourceById(Long id) {
        Resource resource = resourceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
        return ResourceMapper.toDTO(resource);
    }

    @Override
    public List<ResourceDTO> getAllResources() {
        return resourceRepo.findAll().stream()
                .map(ResourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResourceDTO> getResourcesByCondition(String condition) {
        return resourceRepo.findByCondition(condition).stream()
                .map(ResourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResourceDTO> searchResourcesByTitle(String title) {
        return resourceRepo.findByTitleContainingIgnoreCase(title).stream()
                .map(ResourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        Resource existingResource = resourceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));

        existingResource.setTitle(resourceDTO.getTitle());
        existingResource.setContent(resourceDTO.getContent());
        existingResource.setCondition(resourceDTO.getCondition());

        Resource updatedResource = resourceRepo.save(existingResource);
        return ResourceMapper.toDTO(updatedResource);
    }

    @Override
    public void deleteResource(Long id) {
        if (!resourceRepo.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found with ID: " + id);
        }
        resourceRepo.deleteById(id);
    }

    @Override
    public List<ResourceDTO> getResourceRecommendations(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Get all unique conditions from user's medical records
        Set<String> userConditions = user.getMedicalRecords().stream()
                .map(MedicalRecord::getCondition)
                .collect(Collectors.toSet());

        // Get resources matching any of the user's conditions
        List<Resource> recommendedResources = userConditions.stream()
                .flatMap(condition -> resourceRepo.findByConditionIgnoreCase(condition).stream())
                .distinct()
                .collect(Collectors.toList());

        return recommendedResources.stream()
                .map(ResourceMapper::toDTO)
                .collect(Collectors.toList());
    }
}