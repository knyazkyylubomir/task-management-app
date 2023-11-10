package org.project.name.task.management.app.service.user;

import org.project.name.task.management.app.dto.user.UserRegistrationRequest;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;
}
