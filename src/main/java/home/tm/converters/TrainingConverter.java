package home.tm.converters;

import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import home.tm.model.enums.TrainingType;
import home.tm.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingConverter {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseConverter exerciseConverter;

    public List<TrainingDto> toListDto(List<Training> data) {
        return data.stream().map(this::toDto).toList();
    }

    public TrainingDto toDto(Training entity) {
        return toDto(entity, new TrainingDto());
    }

    private TrainingDto toDto(Training entity, TrainingDto dto) {
        dto.setId(entity.getId());
        dto.setNote(entity.getNote());
        dto.setName(entity.getName());
        dto.setWeek(entity.getWeek());
        dto.setDay(entity.getDay());
        dto.setType(entity.getType().name());
        dto.setExercises(exerciseConverter.toListDto(exerciseRepository.findAllByTraining(entity)));
        return dto;
    }

    public Training toEntity(TrainingDto dto, TrainingDiary diary) {
        Training training = toEntity(dto, new Training());
        training.setTrainingDiary(diary);
        return training;
    }

    public Training toEntity(TrainingDto dto, Training entity) {
        entity.setId(dto.getId() == null ? null : dto.getId());
        entity.setNote(dto.getNote());
        entity.setName(dto.getName());
        entity.setType(TrainingType.getType(dto.getType()));
        entity.setDay(dto.getDay());
        entity.setWeek(dto.getWeek());
        entity.setExercises(exerciseConverter.toListEntity(dto.getExercises()));
        return entity;
    }
}
