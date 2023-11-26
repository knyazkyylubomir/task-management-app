package org.project.name.task.management.app.service.task.impl;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.task.TaskRequestDto;
import org.project.name.task.management.app.dto.task.TaskResponseDto;
import org.project.name.task.management.app.dto.task.UpdateTaskRequestDto;
import org.project.name.task.management.app.exception.DuplicateNameException;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.mapper.TaskMapper;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.Task;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.ProjectRepository;
import org.project.name.task.management.app.repository.TaskRepository;
import org.project.name.task.management.app.repository.UserRepository;
import org.project.name.task.management.app.service.task.TaskService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TaskResponseDto createTask(TaskRequestDto requestDto, String username) {
        User owner = findOwner(username);
        User assignee = userRepository.findById(requestDto.getAssigneeId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no assignee by id: " + requestDto.getAssigneeId()
                )
        );
        Project project = projectRepository.findByIdAndUser(requestDto.getProjectId(), owner)
                .orElseThrow(() -> new EntityNotFoundException(
                                "There is no project for user " + username + " by id: "
                                        + requestDto.getProjectId()
                ));
        doesTaskNameDuplicate(project, assignee, requestDto.getName());
        doesPriorityExist(requestDto.getPriority());
        Task.Status status = Task.Status.NOT_STARTED;
        Task task = taskMapper.createEntity(requestDto, status, project, assignee, owner);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public List<TaskResponseDto> getTasks(Long id, String username) {
        User user = findUser(username);
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no project by id: " + id)
        );
        return taskRepository.findAllByProjectAndAssigneeOrOwner(project, user, user).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskResponseDto getTask(Long id, String username) {
        User user = findUser(username);
        Task task = taskRepository.findByIdAndAssigneeOrOwner(id, user, user).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no task for assignee " + username + " by id: " + id
                )
        );
        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto, String username) {
        User assignee = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no assignee by username: " + username)
        );
        Task task = taskRepository.findByIdAndAssignee(id, assignee).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no task for assignee " + username + " by id: " + id
                )
        );
        doesStatusExist(requestDto.getStatus());
        Task mergedEntities = taskMapper.mergeEntities(task, requestDto);
        return taskMapper.toDto(taskRepository.save(mergedEntities));
    }

    @Override
    public void deleteTask(Long id, String username) {
        User owner = findOwner(username);
        Task task = taskRepository.findByIdAndOwner(id, owner).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no task by owner " + username + " by id: " + id
                )
        );
        taskRepository.delete(task);
    }

    private User findOwner(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no owner by username: " + username)
        );
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
    }

    private void doesTaskNameDuplicate(Project project, User assignee, String name) {
        for (Task task : taskRepository.findAllByProjectAndAssignee(project, assignee)) {
            String taskName = task.getName().toLowerCase();
            if (taskName.equals(name.toLowerCase())) {
                throw new DuplicateNameException(
                        "The task with this name already exist in this project"
                );
            }
        }
    }

    private void doesPriorityExist(String requestPriority) {
        Arrays.stream(Task.Priority.values())
                .filter(priority -> priority.name().equals(requestPriority))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no such a priority. Check correctness of a written word"
                ));
    }

    private void doesStatusExist(String requestStatus) {
        Arrays.stream(Task.Status.values())
                .filter(status -> status.name().equals(requestStatus))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no such a status. Check correctness of a written word"
                ));
    }
}
