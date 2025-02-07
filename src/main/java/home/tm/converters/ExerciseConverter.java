package home.tm.converters;

import home.tm.TimeKeeper.api.models.ExerciseDto;
import home.tm.model.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseConverter {

    public List<ExerciseDto> toListDto(List<Exercise> data) {
        return data.stream().map(this::toDto).toList();
    }

    private ExerciseDto toDto(Exercise entity) {
        return toDto(entity, new ExerciseDto());
    }

    private ExerciseDto toDto(Exercise entity, ExerciseDto dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSet(entity.getSet());
        dto.setRep(entity.getRep());
        dto.setSpeed(entity.getSpeed());
        dto.setDistance(entity.getDistance());
        dto.setDuration(entity.getDuration());
        dto.setWeight(entity.getWeight());
        return dto;
    }

    public List<Exercise> toListEntity(List<ExerciseDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    private Exercise toEntity(ExerciseDto dto) {
        return toEntity(dto, new Exercise());
    }

    private Exercise toEntity(ExerciseDto dto, Exercise entity) {
        entity.setId(dto.getId() == null ? null : dto.getId());
        entity.setName(dto.getName());
        entity.setSet(dto.getSet());
        entity.setRep(dto.getRep());
        entity.setSpeed(dto.getSpeed());
        entity.setDistance(dto.getDistance());
        entity.setDuration(dto.getDuration());
        entity.setWeight(dto.getWeight());
        return entity;
    }
}
