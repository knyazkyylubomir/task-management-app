package org.project.name.task.management.app.service.comment.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.comment.CommentRequestDto;
import org.project.name.task.management.app.dto.comment.CommentResponseDto;
import org.project.name.task.management.app.dto.comment.CommentSearchParameter;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.mapper.CommentMapper;
import org.project.name.task.management.app.model.Comment;
import org.project.name.task.management.app.model.Task;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.comment.CommentRepository;
import org.project.name.task.management.app.repository.comment.CommentSpecificationBuilder;
import org.project.name.task.management.app.repository.task.TaskRepository;
import org.project.name.task.management.app.repository.user.UserRepository;
import org.project.name.task.management.app.service.comment.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentSpecificationBuilder commentSpecificationBuilder;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
        Task task = taskRepository.findById(requestDto.getTaskId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no task by id: " + requestDto.getTaskId()
                )
        );
        LocalDateTime timestamp = LocalDateTime.now();
        Comment comment = commentMapper.createEntity(requestDto, task, user, timestamp);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> searchTasks(
            CommentSearchParameter searchParameter,
            Pageable pageable
    ) {
        Specification<Comment> commentSpecification
                = commentSpecificationBuilder.build(searchParameter);
        return commentRepository.findAll(commentSpecification, pageable).stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
