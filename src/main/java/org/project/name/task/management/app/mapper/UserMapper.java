package org.project.name.task.management.app.mapper;

import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.role.UpdateRoleResponseDto;
import org.project.name.task.management.app.dto.user.UpdateUserInfoRequest;
import org.project.name.task.management.app.dto.user.UserInfoResponseDto;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.model.Role;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User createUser(
            String username,
            String password,
            String email,
            String firstName,
            String lastName,
            Set<Role> roles
    );

    UserResponseDto toDto(User user);

    UpdateRoleResponseDto updateRole(User user, String role);

    UserInfoResponseDto toInfoDto(User user);

    User mergeEntities(@MappingTarget User user, UpdateUserInfoRequest request);

    @AfterMapping
    default void setRole(@MappingTarget UserInfoResponseDto responseDto, User user) {
        Role role = user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("There is no roles"));
        responseDto.setRole(role.getRoleName().name());
    }
}
