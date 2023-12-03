package org.project.name.task.management.app.repository.attachment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.exception.SpecificationException;
import org.project.name.task.management.app.model.Attachment;
import org.project.name.task.management.app.repository.SpecificationProvider;
import org.project.name.task.management.app.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AttachmentSpecificationProviderManager
        implements SpecificationProviderManager<Attachment> {
    private final List<SpecificationProvider<Attachment>> attachmentSpecificationProviders;

    @Override
    public SpecificationProvider<Attachment> getSpecificationProvider(String key) {
        return attachmentSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new SpecificationException(
                        "Can't find correct specification provider for key: " + key
                ));
    }
}
