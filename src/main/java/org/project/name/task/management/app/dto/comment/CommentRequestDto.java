package org.project.name.task.management.app.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {
    @NotNull
    private Long taskId;
    @NotEmpty
    @Size(min = 1, max = 2000)
    private String text;
}
