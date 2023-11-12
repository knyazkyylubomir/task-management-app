package org.project.name.task.management.app.service.role.impl;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.role.UpdateRoleRequest;
import org.project.name.task.management.app.dto.role.UpdateRoleResponseDto;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.exception.RoleException;
import org.project.name.task.management.app.mapper.UserMapper;
import org.project.name.task.management.app.model.Role;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.RoleRepository;
import org.project.name.task.management.app.repository.UserRepository;
import org.project.name.task.management.app.service.role.RoleService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UpdateRoleResponseDto updateRole(Long id, UpdateRoleRequest request, String username) {
        if (request.getRole().equals(Role.RoleName.SUPER_ADMIN.name())) {
            throw new RoleException("Only within the DB you can set a user to the super admin");
        }
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
        User userById = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no user by id: " + id)
        );
        Role userRole = user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("There is no roles"));
        isUserAllowedToChangeRole(userRole, userById);
        Role role = Arrays.stream(Role.RoleName.values())
                .filter(roleName -> roleName.name().equals(request.getRole()))
                .map(roleRepository::findRoleByRoleName)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                                "There is no role name: " + request.getRole()
                ));
        userById.setRoles(Set.of(role));
        return userMapper.updateRole(userRepository.save(userById), role.getRoleName().name());
    }

    private void isUserAllowedToChangeRole(Role userRole, User userById) {
        if (!userRole.getRoleName().name().equals(Role.RoleName.SUPER_ADMIN.name())) {
            Role userFromDbRole = userById.getRoles().stream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("There is no roles"));
            if (userFromDbRole.getRoleName().name().equals(Role.RoleName.ADMIN.name())
                    || userFromDbRole.getRoleName().name()
                    .equals(Role.RoleName.SUPER_ADMIN.name())) {
                throw new RoleException("You cannot change roles for admins");
            }
        }
    }
}
