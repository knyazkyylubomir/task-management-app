package org.project.name.task.management.app.dto.task;

public record TaskResponseDto(
        Long id,
        String name,
        String description,
        String priority,
        String status,
        String dueDate,
        Long projectId,
        Long assigneeId
) {
}
