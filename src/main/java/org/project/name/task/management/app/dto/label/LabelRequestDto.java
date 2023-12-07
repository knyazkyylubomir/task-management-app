package org.project.name.task.management.app.dto.label;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LabelRequestDto {
    @NotEmpty
    @Size(max = 64)
    private String name;
    @NotEmpty
    @Size(max = 64)
    private String color;
}
