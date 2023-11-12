package org.project.name.task.management.app.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    @NotBlank
    private String role;
}
