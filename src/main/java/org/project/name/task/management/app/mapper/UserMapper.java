package org.project.name.task.management.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.user.UserRegistrationRequestDto;
import org.project.name.task.management.app.dto.user.UserResponseDto;
import org.project.name.task.management.app.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "password", source = "password")
    User createEntity(UserRegistrationRequestDto request, String password);

    UserResponseDto toDto(User user);
}
