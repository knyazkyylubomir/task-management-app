package org.project.name.task.management.app.repository.comment;

import org.project.name.task.management.app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends
        JpaRepository<Comment, Long>,
        JpaSpecificationExecutor<Comment> {
}
