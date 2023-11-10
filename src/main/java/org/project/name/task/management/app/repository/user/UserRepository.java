package org.project.name.task.management.app.repository.user;

import java.util.Optional;
import org.project.name.task.management.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
