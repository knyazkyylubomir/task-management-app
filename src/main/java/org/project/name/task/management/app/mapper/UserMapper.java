package org.project.name.task.management.app.mapper;

import org.mapstruct.Mapper;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User createUser(
            String username,
            String password,
            String email,
            String firstName,
            String lastName
    );

    UserResponseDto toDto(User user);
}
