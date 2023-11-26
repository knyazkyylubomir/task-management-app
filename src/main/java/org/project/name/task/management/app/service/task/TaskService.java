package org.project.name.task.management.app.service.task;

import java.util.List;
import org.project.name.task.management.app.dto.task.TaskRequestDto;
import org.project.name.task.management.app.dto.task.TaskResponseDto;
import org.project.name.task.management.app.dto.task.UpdateTaskRequestDto;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto requestDto, String username);

    List<TaskResponseDto> getTasks(Long id, String username);

    TaskResponseDto getTask(Long id, String username);

    TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto, String username);

    void deleteTask(Long id, String username);
}
