package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.converters.TrainingConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import home.tm.repositories.TrainingRepository;
import home.tm.services.TrainingDiaryService;
import home.tm.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static home.tm.exceptions.ExceptionMessage.TRAINING_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingConverter trainingConverter;
    private final TrainingDiaryService trainingDiaryService;

    @Override
    public TrainingDto createTraining(Long diaryId, TrainingDto dto) {
        Training training = trainingRepository.save(trainingConverter.toEntity(dto, trainingDiaryService.getTrainingDiaryById(diaryId)));
        return trainingConverter.toDto(training);
    }

    @Override
    public void deleteTraining(Long diaryId, Long id) {
        trainingDiaryService.getTrainingDiaryById(diaryId);
        trainingRepository.deleteById(id);
    }

    @Override
    public TrainingDto getTraining(Long diaryId, Long id) {
        trainingDiaryService.getTrainingDiaryById(diaryId);
        return trainingConverter.toDto(getTrainingById(id));
    }

    @Override
    public List<TrainingDto> getTrainings(Long diaryId, Pageable pageable, String search) {
        TrainingDiary trainingDiary = trainingDiaryService.getTrainingDiaryById(diaryId);
        return trainingConverter.toListDto(trainingRepository.findAllByTrainingDiary(trainingDiary));
    }

    @Override
    public TrainingDto updateTraining(Long diaryId, Long id, TrainingDto dto) {
        trainingDiaryService.getTrainingDiaryById(diaryId);
        Training training = trainingRepository.save(trainingConverter.toEntity(dto, getTrainingById(id)));
        return trainingConverter.toDto(training);
    }

    private Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(TRAINING_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
    }
}
