package org.project.name.task.management.app.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.service.media.type.strategy.MediaTypeStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StrategyConfig {
    private final List<MediaTypeStrategy> mediaTypeStrategies;

    @Bean
    public Map<String, String> getMediaType() {
        Map<String, String> mediaTypeByString = new HashMap<>();
        mediaTypeStrategies.forEach(mediaTypeStrategy -> mediaTypeByString.put(
                mediaTypeStrategy.fileExtension(), mediaTypeStrategy.getMediaType()
        ));
        return mediaTypeByString;
    }
}
