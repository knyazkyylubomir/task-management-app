package org.project.name.task.management.app.service.user;

import org.project.name.task.management.app.dto.user.UpdateUserRequest;
import org.project.name.task.management.app.dto.user.UserRegistrationRequestDto;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

    UserResponseDto getMyProfile(String username);

    UserResponseDto updateMyProfile(UpdateUserRequest request, String username);
}
