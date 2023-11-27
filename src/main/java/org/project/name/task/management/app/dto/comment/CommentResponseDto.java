package org.project.name.task.management.app.dto.comment;

public record CommentResponseDto(
        Long id,
        Long taskId,
        Long userId,
        String text,
        String timestamp
) {
}
