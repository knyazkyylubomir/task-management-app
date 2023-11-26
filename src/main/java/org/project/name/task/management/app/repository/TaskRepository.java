package org.project.name.task.management.app.repository;

import java.util.List;
import java.util.Optional;
import org.project.name.task.management.app.model.Project;
import org.project.name.task.management.app.model.Task;
import org.project.name.task.management.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectAndAssignee(Project project, User assignee);

    List<Task> findAllByProjectAndAssigneeOrOwner(Project project, User assignee, User owner);

    Optional<Task> findByIdAndAssignee(Long id, User assignee);

    Optional<Task> findByIdAndAssigneeOrOwner(Long id, User assignee, User owner);

    Optional<Task> findByIdAndOwner(Long id, User owner);
}
