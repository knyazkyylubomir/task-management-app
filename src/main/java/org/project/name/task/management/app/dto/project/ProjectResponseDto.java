package org.project.name.task.management.app.dto.project;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        String startDate,
        String endDate,
        String status
) {
}
