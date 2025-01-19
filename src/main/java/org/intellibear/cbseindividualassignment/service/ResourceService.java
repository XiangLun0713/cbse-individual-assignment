package org.intellibear.cbseindividualassignment.service;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.ResourceDTO;

public interface ResourceService {
    ResourceDTO createResource(ResourceDTO resourceDTO);

    ResourceDTO getResourceById(Long id);

    List<ResourceDTO> getAllResources();

    List<ResourceDTO> getResourcesByCondition(String condition);

    List<ResourceDTO> searchResourcesByTitle(String title);

    ResourceDTO updateResource(Long id, ResourceDTO resourceDTO);

    void deleteResource(Long id);

    List<ResourceDTO> getResourceRecommendations(Long userId);
}