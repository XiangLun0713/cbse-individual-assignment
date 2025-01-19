package org.intellibear.cbseindividualassignment.mapper;

import org.intellibear.cbseindividualassignment.model.dto.ResourceDTO;
import org.intellibear.cbseindividualassignment.model.entity.Resource;

public class ResourceMapper {
    public static ResourceDTO toDTO(Resource resource) {
        if (resource == null)
            return null;

        ResourceDTO dto = new ResourceDTO();
        dto.setId(resource.getId());
        dto.setTitle(resource.getTitle());
        dto.setContent(resource.getContent());
        dto.setCondition(resource.getCondition());
        dto.setCreatedAt(resource.getCreatedAt());
        return dto;
    }

    public static Resource toEntity(ResourceDTO dto) {
        if (dto == null)
            return null;

        Resource resource = new Resource();
        resource.setTitle(dto.getTitle());
        resource.setContent(dto.getContent());
        resource.setCondition(dto.getCondition());
        return resource;
    }
}