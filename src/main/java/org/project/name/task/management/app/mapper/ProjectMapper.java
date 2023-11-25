package org.project.name.task.management.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.project.ProjectRequestDto;
import org.project.name.task.management.app.dto.project.ProjectResponseDto;
import org.project.name.task.management.app.dto.project.UpdateProjectRequestDto;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Project createEntity(ProjectRequestDto requestDto, Project.Status status, User user);

    ProjectResponseDto toDto(Project project);

    Project mergeEntities(@MappingTarget Project project, UpdateProjectRequestDto requestDto);
}
