package org.project.name.task.management.app.dto.user;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName
) {
}
