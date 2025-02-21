package home.tm.converters;

import home.tm.TimeKeeper.api.models.BodyMeasurementDto;
import home.tm.model.BodyMeasurement;
import home.tm.model.TrainingDiary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BodyMeasurementConverter {

    public BodyMeasurementDto toDto(BodyMeasurement entity) {
        BodyMeasurementDto dto = new BodyMeasurementDto();
        dto.setId(entity.getId());
        dto.setDay(entity.getDay());
        dto.setWeek(entity.getWeek());
        dto.setWeight(entity.getWeight());
        dto.setBodyFat(entity.getBodyFat());
        return dto;
    }

    public List<BodyMeasurementDto> toListDto(List<BodyMeasurement> data) {
        return data.stream().map(this::toDto).collect(Collectors.toList());
    }

    public BodyMeasurement toEntity(BodyMeasurementDto dto, TrainingDiary trainingDiary) {
        return toEntity(dto, new BodyMeasurement(), trainingDiary);
    }

    public BodyMeasurement toEntity(BodyMeasurementDto dto, BodyMeasurement entity, TrainingDiary trainingDiary) {
        entity.setId(dto.getId() == null ? null : dto.getId());
        entity.setDay(dto.getDay());
        entity.setWeek(dto.getWeek());
        entity.setWeight(dto.getWeight());
        entity.setBodyFat(dto.getBodyFat());
        entity.setTrainingDiary(trainingDiary);
        return entity;
    }
}
