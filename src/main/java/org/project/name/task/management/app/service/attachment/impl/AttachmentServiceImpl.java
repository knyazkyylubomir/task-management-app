package org.project.name.task.management.app.service.attachment.impl;

import com.dropbox.core.v2.files.FileMetadata;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.attachment.AttachmentResponseDto;
import org.project.name.task.management.app.dto.attachment.AttachmentSearchParameter;
import org.project.name.task.management.app.dto.attachment.FileDownloadDto;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.exception.FileDuplicateException;
import org.project.name.task.management.app.mapper.AttachmentMapper;
import org.project.name.task.management.app.model.Attachment;
import org.project.name.task.management.app.model.Task;
import org.project.name.task.management.app.repository.attachment.AttachmentRepository;
import org.project.name.task.management.app.repository.attachment.AttachmentSpecificationBuilder;
import org.project.name.task.management.app.repository.task.TaskRepository;
import org.project.name.task.management.app.service.attachment.AttachmentService;
import org.project.name.task.management.app.service.dropbox.client.DropboxClient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentSpecificationBuilder attachmentSpecificationBuilder;
    private final DropboxClient dropboxClient;
    private final AttachmentMapper attachmentMapper;
    private final TaskRepository taskRepository;

    @Override
    public AttachmentResponseDto uploadAttachment(
            Long taskId, MultipartFile file
    ) {
        FileMetadata metadata = dropboxClient.uploadFile(file);
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("There is no task by id: " + taskId)
        );
        String dropboxFileId = metadata.getId();
        if (attachmentRepository.findByTaskAndDropboxFileId(task, dropboxFileId).isPresent()) {
            throw new FileDuplicateException(
                    "The task with name '" + task.getName()
                            + "' already has this file '" + file.getOriginalFilename() + "'"
            );
        }
        String filename = metadata.getName();
        Attachment attachment = attachmentMapper.createEntity(
                task,
                dropboxFileId,
                filename,
                LocalDateTime.now()
        );
        return attachmentMapper.toDto(attachmentRepository.save(attachment));
    }

    @Override
    public FileDownloadDto searchAttachments(
            AttachmentSearchParameter searchParameter
    ) {
        Specification<Attachment> attachmentSpecification
                = attachmentSpecificationBuilder.build(searchParameter);
        Attachment attachment = attachmentRepository.findOne(attachmentSpecification).orElseThrow(
                () -> new EntityNotFoundException("There is no attachment with this taskId")
        );
        return dropboxClient.downloadFiles(attachment.getDropboxFileId());
    }
}
