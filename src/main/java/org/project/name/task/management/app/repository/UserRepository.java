package org.project.name.task.management.app.repository;

import java.util.Optional;
import org.project.name.task.management.app.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.roles")
    @Override
    Optional<User> findById(Long id);

    @EntityGraph(value = "User.roles")
    Optional<User> findUserByUsername(String username);

    @EntityGraph(value = "User.roles")
    Optional<User> findUserByEmail(String email);
}
