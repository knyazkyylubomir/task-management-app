package org.project.name.task.management.app.dto.attachment;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(MultipartFile file, String name) {
}
