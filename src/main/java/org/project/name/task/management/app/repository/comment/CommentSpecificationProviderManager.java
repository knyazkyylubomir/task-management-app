package org.project.name.task.management.app.repository.comment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.exception.SpecificationException;
import org.project.name.task.management.app.model.Comment;
import org.project.name.task.management.app.repository.SpecificationProvider;
import org.project.name.task.management.app.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentSpecificationProviderManager implements SpecificationProviderManager<Comment> {
    private final List<SpecificationProvider<Comment>> commentSpecificationProviders;

    @Override
    public SpecificationProvider<Comment> getSpecificationProvider(String key) {
        return commentSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new SpecificationException(
                        "Can't find correct specification provider for key: " + key
                ));
    }
}
