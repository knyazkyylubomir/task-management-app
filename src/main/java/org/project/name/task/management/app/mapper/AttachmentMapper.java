package org.project.name.task.management.app.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.attachment.AttachmentResponseDto;
import org.project.name.task.management.app.model.Attachment;
import org.project.name.task.management.app.model.Task;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Attachment createEntity(
            Task task,
            String dropboxFileId,
            String filename,
            LocalDateTime updateDate
            );

    @Mapping(target = "taskId", source = "attachment.task.id")
    AttachmentResponseDto toDto(Attachment attachment);
}
