package org.project.name.task.management.app.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.comment.CommentRequestDto;
import org.project.name.task.management.app.dto.comment.CommentResponseDto;
import org.project.name.task.management.app.model.Comment;
import org.project.name.task.management.app.model.Task;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Comment createEntity(
            CommentRequestDto requestDto,
            Task task,
            User user,
            LocalDateTime timestamp
    );

    @Mapping(target = "taskId", source = "comment.task.id")
    @Mapping(target = "userId", source = "comment.user.id")
    CommentResponseDto toDto(Comment comment);
}
