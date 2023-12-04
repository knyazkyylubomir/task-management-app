package org.project.name.task.management.app.service.media.type.strategy.impl;

import org.project.name.task.management.app.service.media.type.strategy.MediaTypeStrategy;
import org.springframework.stereotype.Component;

@Component
public class ZipMediaTypeStrategy implements MediaTypeStrategy {
    @Override
    public String getMediaType() {
        return "application/zip";
    }

    @Override
    public String fileExtension() {
        return "zip";
    }
}
