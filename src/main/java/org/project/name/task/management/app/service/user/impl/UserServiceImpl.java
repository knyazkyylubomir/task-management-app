package org.project.name.task.management.app.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.user.UserRegistrationRequest;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.RegistrationException;
import org.project.name.task.management.app.mapper.UserMapper;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.user.UserRepository;
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
    public UserResponseDto register(UserRegistrationRequest request) throws RegistrationException {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()
                || userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with given username already exists");
        }
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());
        String email = request.getEmail();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        User user = userMapper.createUser(username, password, email, firstName, lastName);
        return userMapper.toDto(userRepository.save(user));
    }
}
