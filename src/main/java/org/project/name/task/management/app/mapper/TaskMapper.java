package org.project.name.task.management.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.task.TaskRequestDto;
import org.project.name.task.management.app.dto.task.TaskResponseDto;
import org.project.name.task.management.app.dto.task.UpdateTaskRequestDto;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.Task;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "requestDto.name")
    @Mapping(target = "description", source = "requestDto.description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", ignore = true)
    Task createEntity(
            TaskRequestDto requestDto,
            Task.Status status,
            Project project,
            User assignee,
            User owner
    );

    Task mergeEntities(@MappingTarget Task task, UpdateTaskRequestDto requestDto);

    @Mapping(target = "projectId", source = "task.project.id")
    @Mapping(target = "assigneeId", source = "task.assignee.id")
    TaskResponseDto toDto(Task task);
}
