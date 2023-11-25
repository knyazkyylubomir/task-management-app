package org.project.name.task.management.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.user.UpdateUserRequestDto;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "Endpoints for managing a user profile")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(
            summary = "Get a user's profile",
            description = "This endpoints gets a user's profile details"
    )
    public UserResponseDto getMyProfile(Authentication authentication) {
        return userService.getMyProfile(authentication.getName());
    }

    @PatchMapping("/me")
    @Operation(
            summary = "Update a user's profile details",
            description = "This endpoints updates a user's profile details"
    )
    public UserResponseDto updateMyProfile(
            @RequestBody @Valid UpdateUserRequestDto request,
            Authentication authentication
    ) {
        return userService.updateMyProfile(request, authentication.getName());
    }
}
