package org.project.name.task.management.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.task.TaskRequestDto;
import org.project.name.task.management.app.dto.task.TaskResponseDto;
import org.project.name.task.management.app.dto.task.UpdateTaskRequestDto;
import org.project.name.task.management.app.service.task.TaskService;
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

@Tag(name = "Task API", description = "Endpoints for managing user tasks")
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a task", description = "This endpoint creates a new task")
    public TaskResponseDto createTask(
            @RequestBody @Valid TaskRequestDto requestDto,
            Authentication authentication
    ) {
        return taskService.createTask(requestDto, authentication.getName());
    }

    @GetMapping("/project/{id}")
    @Operation(
            summary = "Retrieve all tasks",
            description = "This endpoint retrieves all assignee or owner tasks for project"
    )
    public List<TaskResponseDto> getTasks(
            @PathVariable @Min(1) Long id,
            Authentication authentication
    ) {
        return taskService.getTasks(id, authentication.getName());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a task",
            description = "This endpoint retrieves a task by identifier"
    )public TaskResponseDto getTask(
            @PathVariable @Min(1) Long id,
            Authentication authentication
    ) {
        return taskService.getTask(id, authentication.getName());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a task",
            description = "This endpoint updates a task by identifier"
    )
    public TaskResponseDto updateTask(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid UpdateTaskRequestDto requestDto,
            Authentication authentication
    ) {
        return taskService.updateTask(id, requestDto, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a task",
            description = "This endpoint deletes a task by identifier"
    )
    public void deleteTask(@PathVariable @Min(1) Long id, Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
    }
}
