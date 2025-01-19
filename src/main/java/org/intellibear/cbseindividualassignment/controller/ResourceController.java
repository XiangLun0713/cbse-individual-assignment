package org.intellibear.cbseindividualassignment.controller;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.dto.ResourceDTO;
import org.intellibear.cbseindividualassignment.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<ResourceDTO> createResource(@RequestBody ResourceDTO resourceDTO) {
        ResourceDTO createdResource = resourceService.createResource(resourceDTO);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResource(@PathVariable Long id) {
        ResourceDTO resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAllResources() {
        List<ResourceDTO> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/condition/{condition}")
    public ResponseEntity<List<ResourceDTO>> getResourcesByCondition(@PathVariable String condition) {
        List<ResourceDTO> resources = resourceService.getResourcesByCondition(condition);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResourceDTO>> searchResources(@RequestParam String title) {
        List<ResourceDTO> resources = resourceService.searchResourcesByTitle(title);
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> updateResource(
            @PathVariable Long id,
            @RequestBody ResourceDTO resourceDTO) {
        ResourceDTO updatedResource = resourceService.updateResource(id, resourceDTO);
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/recommendations")
    public ResponseEntity<List<ResourceDTO>> getResourceRecommendations(
            @PathVariable Long userId) {
        List<ResourceDTO> recommendations = resourceService.getResourceRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}