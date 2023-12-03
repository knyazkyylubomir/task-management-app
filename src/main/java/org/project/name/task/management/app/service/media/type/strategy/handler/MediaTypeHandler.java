package org.project.name.task.management.app.service.media.type.strategy.handler;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.exception.MediaTypeException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MediaTypeHandler {
    private final Map<String, String> mediaTypeByString;

    public String getMediaType(String mediaType) {
        String mediaTypeOfFile = mediaTypeByString.getOrDefault(mediaType, null);
        if (mediaTypeOfFile == null) {
            throw new MediaTypeException("You cannot download file with this extension: "
                    + mediaType);
        }
        return mediaTypeOfFile;
    }
}
