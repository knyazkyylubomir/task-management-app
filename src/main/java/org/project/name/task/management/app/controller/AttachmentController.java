package org.project.name.task.management.app.controller;

import com.dropbox.core.DbxException;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.attachment.AttachmentResponseDto;
import org.project.name.task.management.app.dto.attachment.AttachmentSearchParameter;
import org.project.name.task.management.app.dto.attachment.FileDownloadDto;
import org.project.name.task.management.app.service.attachment.AttachmentService;
import org.project.name.task.management.app.service.media.type.strategy.handler.MediaTypeHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Attachments API", description = "Endpoints for managing attachments to a task")
@RequiredArgsConstructor
@RestController
@RequestMapping("/attachments")
@Validated
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final MediaTypeHandler mediaTypeHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AttachmentResponseDto uploadAttachment(
            @RequestParam("taskId") Long taskId,
            @RequestParam("file") MultipartFile file)
            throws IOException, DbxException {
        return attachmentService.uploadAttachment(taskId, file);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<byte[]> downloadAttachment(
            AttachmentSearchParameter searchParameter
    ) {
        FileDownloadDto file = attachmentService.searchAttachments(searchParameter);
        String mediaType = mediaTypeHandler.getMediaType(file.fileType());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                        + URLEncoder.encode(file.fileName(), StandardCharsets.UTF_8))
                .contentType(MediaType.valueOf(mediaType))
                .body(file.file());
    }
}
