package org.project.name.task.management.app.repository.attachment;

import org.project.name.task.management.app.model.Attachment;
import org.project.name.task.management.app.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AttachmentTaskSpecificationProvider implements SpecificationProvider<Attachment> {
    @Override
    public String getKey() {
        return "task";
    }

    @Override
    public Specification<Attachment> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.join("task")
                .get("id")
                .in(Long.parseLong(param));
    }
}
