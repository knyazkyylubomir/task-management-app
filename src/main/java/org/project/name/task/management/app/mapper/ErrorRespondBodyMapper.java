package org.project.name.task.management.app.mapper;

import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.model.ErrorRespondBody;
import org.springframework.http.HttpStatus;

@Mapper(config = MapperConfig.class)
public interface ErrorRespondBodyMapper {
    ErrorRespondBody createErrorBody(
            LocalDateTime timestamp, HttpStatus status, List<String> errors
    );
}
