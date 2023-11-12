package org.project.name.task.management.app.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserInfoRequest {
    @Size(min = 1, max = 64)
    private String username;
    @Email
    @Size(min = 1, max = 64)
    private String email;
    @Size(min = 3, max = 50)
    private String firstName;
    @Size(min = 3, max = 50)
    private String lastName;
}
