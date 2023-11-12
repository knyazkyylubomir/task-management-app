package org.project.name.task.management.app.service.role;

import org.project.name.task.management.app.dto.role.UpdateRoleRequest;
import org.project.name.task.management.app.dto.role.UpdateRoleResponseDto;

public interface RoleService {
    UpdateRoleResponseDto updateRole(Long id, UpdateRoleRequest request, String username);
}
