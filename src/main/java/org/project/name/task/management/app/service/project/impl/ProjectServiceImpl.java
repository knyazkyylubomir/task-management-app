package org.project.name.task.management.app.service.project.impl;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.project.ProjectRequestDto;
import org.project.name.task.management.app.dto.project.ProjectResponseDto;
import org.project.name.task.management.app.dto.project.UpdateProjectRequestDto;
import org.project.name.task.management.app.exception.DuplicateNameException;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.mapper.ProjectMapper;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.User;
import org.project.name.task.management.app.repository.ProjectRepository;
import org.project.name.task.management.app.repository.UserRepository;
import org.project.name.task.management.app.service.project.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto requestDto, String username) {
        User user = findUser(username);
        doesProjectNameDuplicate(user, requestDto.getName());
        Project.Status status = Project.Status.INITIATED;
        Project project = projectMapper.createEntity(requestDto, status, user);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDto> getProjects(String username, Pageable pageable) {
        User user = findUser(username);
        return projectRepository.findAllByUser(user, pageable).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectResponseDto getProject(Long id, String username) {
        User user = findUser(username);
        Project project = findProject(id, user, username);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectResponseDto updateProject(
            Long id,
            UpdateProjectRequestDto requestDto,
            String username
    ) {
        User user = findUser(username);
        doesProjectNameRepeated(user, requestDto.getName(), id);
        Project project = findProject(id, user, username);
        doesStatusExist(requestDto.getStatus());
        Project mergedEntities = projectMapper.mergeEntities(project, requestDto);
        return projectMapper.toDto(projectRepository.save(mergedEntities));
    }

    @Override
    public void deleteProject(Long id, String username) {
        User user = findUser(username);
        Project project = findProject(id, user, username);
        projectRepository.delete(project);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
    }

    private Project findProject(Long id, User user, String username) {
        return projectRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no project for user " + username + " by id: " + id
                )
        );
    }

    private void doesProjectNameDuplicate(User user, String name) {
        for (Project project : projectRepository.findAllByUser(user)) {
            String projectName = project.getName().toLowerCase();
            if (projectName.equals(name.toLowerCase())) {
                throw new DuplicateNameException("The project with this name already exist");
            }
        }
    }

    private void doesProjectNameRepeated(User user, String name, Long id) {
        for (Project project : projectRepository.findAllByUser(user)) {
            String projectName = project.getName().toLowerCase();
            if (projectName.equals(name.toLowerCase())) {
                if (!project.getId().equals(id)) {
                    throw new DuplicateNameException("The project with this name already exist");
                }
                break;
            }
        }
    }

    private void doesStatusExist(String requestStatus) {
        Arrays.stream(Project.Status.values())
                .filter(status -> status.name().equals(requestStatus))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no such a status. Check correctness of a written word"
                ));
    }
}
