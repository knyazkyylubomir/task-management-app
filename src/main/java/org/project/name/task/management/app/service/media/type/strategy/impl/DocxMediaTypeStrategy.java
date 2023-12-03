package org.project.name.task.management.app.service.media.type.strategy.impl;

import org.project.name.task.management.app.service.media.type.strategy.MediaTypeStrategy;
import org.springframework.stereotype.Component;

@Component
public class DocxMediaTypeStrategy implements MediaTypeStrategy {
    @Override
    public String getMediaType() {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    }

    @Override
    public String fileExtension() {
        return "docx";
    }
}
