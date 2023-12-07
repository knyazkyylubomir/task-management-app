package org.project.name.task.management.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.label.LabelRequestDto;
import org.project.name.task.management.app.dto.label.LabelResponseDto;
import org.project.name.task.management.app.service.label.LabelService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Label API", description = "Endpoints for managing labels")
@RequiredArgsConstructor
@RestController
@RequestMapping("/labels")
@Validated
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    @Operation(summary = "Create a new label", description = "This endpoint creates a new label")
    public LabelResponseDto createLabel(@RequestBody @Valid LabelRequestDto requestDto) {
        return labelService.createLabel(requestDto);
    }

    @GetMapping
    @Operation(summary = "Retrieve labels", description = "This endpoint retrieves labels")
    public List<LabelResponseDto> retrieveLabels(Pageable pageable) {
        return labelService.getLabels(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a label", description = "This endpoint updates a label")
    public LabelResponseDto updateLabel(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid LabelRequestDto requestDto
    ) {
        return labelService.updateLabel(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a label", description = "This endpoint deletes a label")
    public void deleteLabel(@PathVariable @Min(1) Long id) {
        labelService.deleteLabel(id);
    }
}
