package org.project.name.task.management.app.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.user.UpdateUserRequest;
import org.project.name.task.management.app.dto.user.UserRegistrationRequestDto;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.exception.RegistrationException;
import org.project.name.task.management.app.exception.UpdateException;
import org.project.name.task.management.app.mapper.UserMapper;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.UserRepository;
import org.project.name.task.management.app.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RegistrationException("User with given username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with given email already exists");
        }
        User user = userMapper.createEntity(
                request,
                passwordEncoder.encode(request.getPassword())
        );
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getMyProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateMyProfile(UpdateUserRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UpdateException("User with given username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UpdateException("User with given email already exists");
        }
        User mergedEntities = userMapper.mergeEntities(user, request);
        return userMapper.toDto(userRepository.save(mergedEntities));
    }
}
