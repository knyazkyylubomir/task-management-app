package org.project.name.task.management.app.service.label;

import java.util.List;
import org.project.name.task.management.app.dto.label.LabelRequestDto;
import org.project.name.task.management.app.dto.label.LabelResponseDto;
import org.springframework.data.domain.Pageable;

public interface LabelService {
    LabelResponseDto createLabel(LabelRequestDto requestDto);

    List<LabelResponseDto> getLabels(Pageable pageable);

    LabelResponseDto updateLabel(Long id, LabelRequestDto requestDto);

    void deleteLabel(Long id);
}
