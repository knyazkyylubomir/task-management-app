package org.project.name.task.management.app.service.media.type.strategy.impl;

import org.project.name.task.management.app.service.media.type.strategy.MediaTypeStrategy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class PngMediaTypeStrategy implements MediaTypeStrategy {
    @Override
    public String getMediaType() {
        return MediaType.IMAGE_JPEG_VALUE;
    }

    @Override
    public String fileExtension() {
        return "png";
    }
}
