package org.project.name.task.management.app.service.project;

import java.util.List;
import org.project.name.task.management.app.dto.project.ProjectRequestDto;
import org.project.name.task.management.app.dto.project.ProjectResponseDto;
import org.project.name.task.management.app.dto.project.UpdateProjectRequestDto;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto requestDto, String username);

    List<ProjectResponseDto> getProjects(String username, Pageable pageable);

    ProjectResponseDto getProject(Long id, String username);

    ProjectResponseDto updateProject(Long id, UpdateProjectRequestDto requestDto, String username);

    void deleteProject(Long id, String username);
}
