package org.project.name.task.management.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.user.UserLoginRequest;
import org.project.name.task.management.app.dto.user.UserLoginResponseDto;
import org.project.name.task.management.app.dto.user.UserRegistrationRequest;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.RegistrationException;
import org.project.name.task.management.app.security.AuthenticationService;
import org.project.name.task.management.app.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequest request)
            throws RegistrationException {
        return userService.register(request);
    }
}