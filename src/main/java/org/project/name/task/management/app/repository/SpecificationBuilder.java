package org.project.name.task.management.app.repository;

import org.project.name.task.management.app.dto.comment.CommentSearchParameter;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(CommentSearchParameter searchParameter);
}
