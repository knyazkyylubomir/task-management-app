package org.project.name.task.management.app.dto.role;

import lombok.Data;

@Data
public class UpdateRoleResponseDto {
    private Long id;
    private String username;
    private String email;
    private String role;
}
