package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.converters.TrainingConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import home.tm.repositories.TrainingRepository;
import home.tm.services.TrainingDiaryService;
import home.tm.services.TrainingService;
import home.tm.utils.ParamsParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map<String, String> filters = ParamsParser.parseSearchQuery(search);

        List<Training> trainings = trainingRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.containsKey("week")) {
                predicates.add(criteriaBuilder.equal(root.get("week"), Integer.parseInt(filters.get("week"))));
            }
            predicates.add(criteriaBuilder.equal(root.get("trainingDiary"), trainingDiary.getId()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return trainingConverter.toListDto(trainings);
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
