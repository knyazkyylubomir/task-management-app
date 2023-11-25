package org.project.name.task.management.app.dto.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProjectRequestDto {
    @NotEmpty
    @Size(min = 1, max = 64)
    private String name;
    @Size(max = 1000)
    private String description;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;
    @NotEmpty
    private String status;
}
