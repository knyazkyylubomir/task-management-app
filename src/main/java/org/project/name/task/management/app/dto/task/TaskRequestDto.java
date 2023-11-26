package org.project.name.task.management.app.dto.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDto {
    @NotEmpty
    @Size(min = 1, max = 64)
    private String name;
    @Size(max = 1000)
    private String description;
    @NotEmpty
    private String priority;
    @NotEmpty
    private String dueDate;
    @NotNull
    private Long projectId;
    @NotNull
    private Long assigneeId;
}
