package org.project.name.task.management.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.comment.CommentRequestDto;
import org.project.name.task.management.app.dto.comment.CommentResponseDto;
import org.project.name.task.management.app.dto.comment.CommentSearchParameter;
import org.project.name.task.management.app.service.comment.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment API", description = "Endpoints for managing comments")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a comment",
            description = "This endpoint adds a new comment for a task"
    )
    public CommentResponseDto addComment(
            @RequestBody @Valid CommentRequestDto requestDto,
            Authentication authentication
    ) {
        return commentService.createComment(requestDto, authentication.getName());
    }

    @GetMapping
    @Operation(
            summary = "Retrieve comments for a task",
            description = "This endpoint retrieves all comments for a task by a task identifier"
    )
    public List<CommentResponseDto> getComments(
            CommentSearchParameter searchParameter,
            Pageable pageable
    ) {
        return commentService.searchTasks(searchParameter, pageable);
    }
}
