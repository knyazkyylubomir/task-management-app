package org.project.name.task.management.app.repository;

import java.util.List;
import java.util.Optional;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUser(User user);

    List<Project> findAllByUser(User user, Pageable pageable);

    Optional<Project> findByUserAndId(User user, Long id);
}
