package org.project.name.task.management.app.exception;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.mapper.ErrorRespondBodyMapper;
import org.project.name.task.management.app.model.ErrorRespondBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorRespondBodyMapper errorRespondBodyMapper;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .sorted()
                .toList();
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(),
                status,
                errors
        );
        return new ResponseEntity<>(body, headers, statusCode);
    }

    @ExceptionHandler(value = RegistrationException.class)
    protected ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(ex.getMessage());
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<String> errors = List.of(ex.getMessage());
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = UpdateException.class)
    protected ResponseEntity<Object> handleUpdateException(UpdateException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(ex.getMessage());
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = DuplicateNameException.class)
    protected ResponseEntity<Object> handleDuplicateNameException(DuplicateNameException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(ex.getMessage());
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of("ID must be greater than or equal to 1");
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = FileDuplicateException.class)
    protected ResponseEntity<Object> handleFileDuplicateException(
            FileDuplicateException ex
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(ex.getMessage());
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = MediaTypeException.class)
    protected ResponseEntity<Object> handleMediaTypeException(
            MediaTypeException ex
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(ex.getMessage());
        ErrorRespondBody body = errorRespondBodyMapper.createErrorBody(
                LocalDateTime.now(), status, errors);
        return new ResponseEntity<>(body, status);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return field + " " + message;
        }
        return e.getDefaultMessage();
    }
}
