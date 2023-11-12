package org.project.name.task.management.app.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.role.UpdateRoleRequest;
import org.project.name.task.management.app.dto.role.UpdateRoleResponseDto;
import org.project.name.task.management.app.dto.user.UpdateUserInfoRequest;
import org.project.name.task.management.app.dto.user.UserInfoResponseDto;
import org.project.name.task.management.app.exception.UpdateException;
import org.project.name.task.management.app.service.role.RoleService;
import org.project.name.task.management.app.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final RoleService roleService;
    private final UserService userService;

    @PutMapping("/{id}/role")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public UpdateRoleResponseDto updateUserRole(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid UpdateRoleRequest request,
            Authentication authentication
    ) {
        return roleService.updateRole(id, request, authentication.getName());
    }

    @GetMapping("/me")
    public UserInfoResponseDto getMyProfile(Authentication authentication) {
        return userService.getMyProfile(authentication.getName());
    }

    @PatchMapping("/me")
    public UserInfoResponseDto updateMyProfile(
            @RequestBody @Valid UpdateUserInfoRequest request,
            Authentication authentication
    ) throws UpdateException {
        return userService.updateMyProfile(request, authentication.getName());
    }
}
