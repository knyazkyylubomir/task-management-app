package org.project.name.task.management.app.service.user.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.user.UpdateUserInfoRequest;
import org.project.name.task.management.app.dto.user.UserInfoResponseDto;
import org.project.name.task.management.app.dto.user.UserRegistrationRequest;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.exception.RegistrationException;
import org.project.name.task.management.app.exception.UpdateException;
import org.project.name.task.management.app.mapper.UserMapper;
import org.project.name.task.management.app.model.Role;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.RoleRepository;
import org.project.name.task.management.app.repository.UserRepository;
import org.project.name.task.management.app.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequest request) throws RegistrationException {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new RegistrationException("User with given username already exists");
        }
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with given email already exists");
        }
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());
        String email = request.getEmail();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        Role.RoleName roleName = Role.RoleName.USER;
        Role role = roleRepository.findRoleByRoleName(roleName).orElseThrow(
                () -> new EntityNotFoundException("There is no role name: " + roleName)
        );
        User user = userMapper.createUser(
                username,
                password,
                email,
                firstName,
                lastName,
                Set.of(role)
        );
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserInfoResponseDto getMyProfile(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
        return userMapper.toInfoDto(user);
    }

    @Override
    public UserInfoResponseDto updateMyProfile(
            UpdateUserInfoRequest request,
            String username
    ) throws UpdateException {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new UpdateException("User with given username already exists");
        }
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new UpdateException("User with given email already exists");
        }
        User updatedUser = userMapper.mergeEntities(user, request);
        return userMapper.toInfoDto(userRepository.save(updatedUser));
    }
}
