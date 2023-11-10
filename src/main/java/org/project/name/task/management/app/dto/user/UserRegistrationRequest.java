package org.project.name.task.management.app.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.project.name.task.management.app.validation.FieldMatch;

@Data
@FieldMatch.List({
        @FieldMatch(
                field = "password",
                fieldMatch = "repeatPassword"
        )
})
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 1, max = 64)
    private String username;
    @NotBlank
    @Size(min = 8, max = 24)
    private String password;
    @NotBlank
    @Size(min = 8, max = 24)
    private String repeatPassword;
    @Email
    @NotBlank
    @Size(min = 1, max = 64)
    private String email;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;
}
