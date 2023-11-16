package org.project.name.task.management.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.project.ProjectRequestDto;
import org.project.name.task.management.app.dto.project.ProjectResponseDto;
import org.project.name.task.management.app.dto.project.UpdateProjectRequest;
import org.project.name.task.management.app.service.project.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Project API", description = "Endpoints for managing details of a project")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
@Validated
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a project", description = "This endpoint creates a new project")
    public ProjectResponseDto createProject(
            @RequestBody @Valid ProjectRequestDto requestDto,
            Authentication authentication) {
        return projectService.createProject(requestDto, authentication.getName());
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all projects",
            description = "This endpoint retrieves all user's projects"
    )
    public List<ProjectResponseDto> retrieveProjects(
            Authentication authentication,
            Pageable pageable
    ) {
        return projectService.getProjects(authentication.getName(), pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a project",
            description = "This endpoint retrieves a project by identifier"
    )
    public ProjectResponseDto retrieveProjectDetails(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return projectService.getProject(id, authentication.getName());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a project",
            description = "This endpoint updates a project by identifier"
    )
    public ProjectResponseDto updateProject(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid UpdateProjectRequest request,
            Authentication authentication
    ) {
        return projectService.updateProject(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a project",
            description = "This endpoint deletes a project by identifier"
    )
    public void deleteProject(@PathVariable @Min(1) Long id, Authentication authentication) {
        projectService.deleteProject(id, authentication.getName());
    }
}
