package org.project.name.task.management.app.service.user;

import org.project.name.task.management.app.dto.user.UpdateUserInfoRequest;
import org.project.name.task.management.app.dto.user.UserInfoResponseDto;
import org.project.name.task.management.app.dto.user.UserRegistrationRequest;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.RegistrationException;
import org.project.name.task.management.app.exception.UpdateException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;

    UserInfoResponseDto getMyProfile(String username);

    UserInfoResponseDto updateMyProfile(
            UpdateUserInfoRequest request,
            String username
    ) throws UpdateException;
}
