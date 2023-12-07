package org.project.name.task.management.app.service.label.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.label.LabelRequestDto;
import org.project.name.task.management.app.dto.label.LabelResponseDto;
import org.project.name.task.management.app.exception.EntityNotFoundException;
import org.project.name.task.management.app.mapper.LabelMapper;
import org.project.name.task.management.app.model.Label;
import org.project.name.task.management.app.repository.label.LabelRepository;
import org.project.name.task.management.app.service.label.LabelService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelResponseDto createLabel(LabelRequestDto requestDto) {
        Label label = labelMapper.createEntity(requestDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public List<LabelResponseDto> getLabels(Pageable pageable) {
        return labelRepository.findAll(pageable).stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelResponseDto updateLabel(Long id, LabelRequestDto requestDto) {
        Label label = getLabelById(id);
        Label mergedLabel = labelMapper.mergeEntities(label, requestDto);
        return labelMapper.toDto(labelRepository.save(mergedLabel));
    }

    @Override
    public void deleteLabel(Long id) {
        Label label = getLabelById(id);
        labelRepository.delete(label);
    }

    private Label getLabelById(Long id) {
        return labelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "There is no label by id: " + id
        ));
    }
}
