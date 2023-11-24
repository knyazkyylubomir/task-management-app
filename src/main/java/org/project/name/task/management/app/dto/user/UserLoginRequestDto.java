package org.project.name.task.management.app.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotEmpty
    @Size(min = 1, max = 64)
    private String username;
    @NotEmpty
    @Size(min = 8, max = 24)
    private String password;
}
