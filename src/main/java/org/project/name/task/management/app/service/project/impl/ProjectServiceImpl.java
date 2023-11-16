package org.project.name.task.management.app.service.project.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.project.ProjectRequestDto;
import org.project.name.task.management.app.dto.project.ProjectResponseDto;
import org.project.name.task.management.app.dto.project.UpdateProjectRequest;
import org.project.name.task.management.app.exception.DateException;
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
        User user = getUserFromDb(username);
        isProjectCorrect(user, requestDto);
        LocalDate startDate = LocalDate.parse(requestDto.getStartDate());
        LocalDate endDate = LocalDate.parse(requestDto.getEndDate());
        isDateCorrect(startDate, endDate);
        Project.Status status = Project.Status.INITIATED;
        Project project = projectMapper.createProject(requestDto, user, status);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDto> getProjects(String username, Pageable pageable) {
        User user = getUserFromDb(username);
        return projectMapper.toDtoList(projectRepository.findAllByUser(user, pageable));
    }

    @Override
    public ProjectResponseDto getProject(Long id, String username) {
        User user = getUserFromDb(username);
        Project project = getProjectFromDb(user, id, username);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectResponseDto updateProject(
            Long id,
            UpdateProjectRequest request,
            String username
    ) {
        User user = getUserFromDb(username);
        isProjectCorrect(user, request, id);
        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());
        isDateCorrect(startDate, endDate);
        Arrays.stream(Project.Status.values())
                .filter(value -> value.name().equals(request.getStatus()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no such a status. Check correctness of a written word"
                ));
        Project project = projectMapper.createProject(id, request, user);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long id, String username) {
        User user = getUserFromDb(username);
        Project project = getProjectFromDb(user, id, username);
        projectRepository.delete(project);
    }

    private User getUserFromDb(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("There is no user by username: " + username)
        );
    }

    private void isDateCorrect(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new DateException("The start date cannot be earlier than today");
        }
        if (endDate.isBefore(startDate) || startDate.isEqual(endDate)) {
            throw new DateException("The end date cannot be earlier than the start date "
                    + "or equal to the start date");
        }
    }

    private void isProjectCorrect(User user, ProjectRequestDto requestDto) {
        for (Project project : projectRepository.findAllByUser(user)) {
            if (project.getName().equals(requestDto.getName())) {
                throw new DuplicateNameException("The project with this name already exist");
            }
        }
    }

    private void isProjectCorrect(User user, UpdateProjectRequest request, Long id) {
        for (Project project : projectRepository.findAllByUser(user)) {
            if (project.getName().equals(request.getName())) {
                if (!project.getId().equals(id)) {
                    throw new DuplicateNameException("The project with this name already exist");
                }
            }
        }
    }

    private Project getProjectFromDb(User user, Long id, String username) {
        return projectRepository.findByUserAndId(user, id).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no project for user: " + username + ", by id: " + id
                )
        );
    }
}
