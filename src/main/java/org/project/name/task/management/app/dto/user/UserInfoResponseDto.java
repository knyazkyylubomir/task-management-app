package org.project.name.task.management.app.dto.user;

import lombok.Data;

@Data
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
}
