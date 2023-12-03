package org.project.name.task.management.app.dto.attachment;

public record FileDownloadDto(byte[] file, String fileName, String fileType) {
}
