package org.project.name.task.management.app.dto.attachment;

public record AttachmentResponseDto(
        Long id,
        Long taskId,
        String dropboxFileId,
        String filename,
        String updateDate
) {
}
