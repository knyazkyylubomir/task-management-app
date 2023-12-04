package org.project.name.task.management.app.repository.attachment;

import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.attachment.AttachmentSearchParameter;
import org.project.name.task.management.app.model.Attachment;
import org.project.name.task.management.app.repository.SpecificationBuilder;
import org.project.name.task.management.app.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AttachmentSpecificationBuilder
        implements SpecificationBuilder<Attachment, AttachmentSearchParameter> {
    private final SpecificationProviderManager<Attachment> specificationProviderManager;

    @Override
    public Specification<Attachment> build(AttachmentSearchParameter searchParameter) {
        Specification<Attachment> spec = Specification.where(null);
        if (searchParameter.taskId() != null && !searchParameter.taskId().isEmpty()) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("task")
                    .getSpecification(searchParameter.taskId()));
        }
        return spec;
    }
}
