package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.TrainingDiaryBaseDto;
import home.tm.TimeKeeper.api.models.TrainingDiaryDto;
import home.tm.converters.TrainingDiaryConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.TrainingDiary;
import home.tm.repositories.TrainingDiaryRepository;
import home.tm.security.service.SecurityService;
import home.tm.services.TrainingDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static home.tm.exceptions.ExceptionMessage.TRAINING_DIARY_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.USER_IS_NOT_AUTHORIZED;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class TrainingDiaryServiceImpl implements TrainingDiaryService {

    private final TrainingDiaryRepository trainingDiaryRepository;
    private final TrainingDiaryConverter trainingDiaryConverter;
    private final SecurityService securityService;


    @Override
    public TrainingDiaryDto createTrainingDiary(TrainingDiaryBaseDto dto) {
        TrainingDiary trainingDiary = trainingDiaryRepository.save(trainingDiaryConverter.toEntity(dto));
        return trainingDiaryConverter.toDto(trainingDiary);
    }

    @Override
    public void deleteTrainingDiary(Long id) {
        TrainingDiary trainingDiary = getTrainingDiaryById(id);
        trainingDiaryRepository.delete(trainingDiary);
    }



    @Override
    public TrainingDiaryDto getTrainingDiary(Long id) {
        return trainingDiaryConverter.toDto(getTrainingDiaryById(id));
    }

    @Override
    public TrainingDiaryDto updateTrainingDiary(Long id, TrainingDiaryBaseDto dto) {
        TrainingDiary trainingDiary = trainingDiaryRepository.save(trainingDiaryConverter.toEntity(dto, getTrainingDiaryById(id)));
        return trainingDiaryConverter.toDto(trainingDiary);
    }

    @Override
    public TrainingDiary getTrainingDiaryById(Long id) {
        TrainingDiary trainingDiary = trainingDiaryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(TRAINING_DIARY_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
        Long userId = securityService.getCurrentUser().getId();
        if (!trainingDiary.getUser().getId().equals(userId)) {
            throw new NotFoundException(NEBYLO_NALEZENO, String.format(USER_IS_NOT_AUTHORIZED.getMessage(), userId), ERROR);
        }
        return trainingDiary;
    }

    @Override
    public List<TrainingDiaryBaseDto> getTrainingDiaries() {
        return trainingDiaryConverter.toListBaseDto(trainingDiaryRepository.findAllByUser(securityService.getCurrentUser()));
    }
}
