package org.project.name.task.management.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.name.task.management.app.config.MapperConfig;
import org.project.name.task.management.app.dto.label.LabelRequestDto;
import org.project.name.task.management.app.dto.label.LabelResponseDto;
import org.project.name.task.management.app.model.Label;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {
    Label createEntity(LabelRequestDto requestDto);

    LabelResponseDto toDto(Label label);

    Label mergeEntities(@MappingTarget Label label, LabelRequestDto requestDto);
}
