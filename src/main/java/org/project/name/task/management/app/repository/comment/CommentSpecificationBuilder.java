package org.project.name.task.management.app.repository.comment;

import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.comment.CommentSearchParameter;
import org.project.name.task.management.app.model.Comment;
import org.project.name.task.management.app.repository.SpecificationBuilder;
import org.project.name.task.management.app.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentSpecificationBuilder implements SpecificationBuilder<Comment> {
    private final SpecificationProviderManager<Comment> specificationProviderManager;

    @Override
    public Specification<Comment> build(CommentSearchParameter searchParameter) {
        Specification<Comment> spec = Specification.where(null);
        if (searchParameter.taskId() != null && !searchParameter.taskId().isEmpty()) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("task")
                    .getSpecification(searchParameter.taskId()));
        }
        return spec;
    }
}
