package org.project.name.task.management.app.dto.task;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateTaskRequestDto {
    @NotEmpty
    private String status;
}
