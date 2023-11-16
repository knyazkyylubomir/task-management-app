package org.project.name.task.management.app.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.project.ProjectRequestDto;
import org.project.name.task.management.app.dto.project.ProjectResponseDto;
import org.project.name.task.management.app.dto.project.UpdateProjectRequest;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Project createProject(ProjectRequestDto requestDto, User user, Project.Status status);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "deleted", ignore = true)
    Project createProject(Long id, UpdateProjectRequest request, User user);

    ProjectResponseDto toDto(Project project);

    List<ProjectResponseDto> toDtoList(List<Project> projects);
}
