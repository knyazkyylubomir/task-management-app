package org.project.name.task.management.app.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorRespondBody {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private List<String> errors;
}
