package org.project.name.task.management.app.service.comment;

import java.util.List;
import org.project.name.task.management.app.dto.comment.CommentRequestDto;
import org.project.name.task.management.app.dto.comment.CommentResponseDto;
import org.project.name.task.management.app.dto.comment.CommentSearchParameter;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto requestDto, String username);

    List<CommentResponseDto> searchTasks(CommentSearchParameter searchParameter, Pageable pageable);
}
