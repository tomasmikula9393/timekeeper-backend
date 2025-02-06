package home.tm.converters;

import home.tm.TimeKeeper.api.models.TrainingDiaryBaseDto;
import home.tm.TimeKeeper.api.models.TrainingDiaryDto;
import home.tm.model.TrainingDiary;
import home.tm.repositories.TrainingRepository;
import home.tm.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingDiaryConverter {

    private final SecurityService securityService;
    private final TrainingRepository trainingRepository;
    private final TrainingConverter trainingConverter;

    public TrainingDiary toEntity(TrainingDiaryDto dto) {
        return toEntity(dto, new TrainingDiary());
    }

    public TrainingDiary toEntity(TrainingDiaryDto dto, TrainingDiary entity) {
        entity.setId(dto.getId() == null ? null : dto.getId());
        entity.setYear(dto.getYear());
        entity.setWeek(dto.getWeek());
        entity.setName(dto.getName());
        entity.setUser(securityService.getCurrentUser());
        return entity;
    }

    public TrainingDiaryDto toDto(TrainingDiary entity) {
        TrainingDiaryDto dto = toBaseDto(entity, new TrainingDiaryDto());
        dto.setTrainings(trainingConverter.toListDto(trainingRepository.findAllByTrainingDiary(entity)));
        return dto;
    }
    public TrainingDiaryBaseDto toBaseDto(TrainingDiary entity) {
        return toBaseDto(entity, new TrainingDiaryBaseDto());
    }

    public <T extends TrainingDiaryBaseDto> T toBaseDto(TrainingDiary entity, T dto) {
        dto.setId(entity.getId());
        dto.setYear(entity.getYear());
        dto.setWeek(entity.getWeek());
        dto.setName(entity.getName());
        return dto;
    }

    public List<TrainingDiaryDto> toListDto(List<TrainingDiary> data) {
        return data.stream().map(this::toDto).toList();
    }
    public List<TrainingDiaryBaseDto> toListBaseDto(List<TrainingDiary> data) {
        return data.stream().map(this::toBaseDto).toList();
    }
}
