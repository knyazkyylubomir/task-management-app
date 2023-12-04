package org.project.name.task.management.app.repository.comment;

import org.project.name.task.management.app.model.Comment;
import org.project.name.task.management.app.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CommentTaskSpecificationProvider implements SpecificationProvider<Comment> {
    @Override
    public String getKey() {
        return "task";
    }

    public Specification<Comment> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.join("task")
                .get("id")
                .in(Long.parseLong(param));
    }
}
