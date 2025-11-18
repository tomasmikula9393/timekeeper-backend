package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.converters.TrainingConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import home.tm.repositories.TrainingDiaryRepository;
import home.tm.repositories.TrainingRepository;
import home.tm.services.TrainingService;
import home.tm.utils.ParamsParser;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static home.tm.exceptions.ExceptionMessage.TRAINING_DIARY_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.TRAINING_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingConverter trainingConverter;
    private final TrainingDiaryRepository trainingDiaryRepository;

    @Override
    public TrainingDto createTraining(Long diaryId, TrainingDto dto) {
        Training training = trainingRepository.save(trainingConverter.toEntity(dto, getTrainingDiaryById(diaryId)));
        return trainingConverter.toDto(training);
    }

    @Override
    public void deleteTraining(Long diaryId, Long id) {
        getTrainingDiaryById(diaryId);
        trainingRepository.deleteById(id);
    }

    @Override
    public TrainingDto getTraining(Long diaryId, Long id) {
        getTrainingDiaryById(diaryId);
        return trainingConverter.toDto(getTrainingById(id));
    }

    @Override
    public List<TrainingDto> getTrainings(Long diaryId, Pageable pageable, String search) {
        TrainingDiary trainingDiary = getTrainingDiaryById(diaryId);
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
        getTrainingDiaryById(diaryId);
        Training training = trainingRepository.save(trainingConverter.toEntity(dto, getTrainingById(id)));
        return trainingConverter.toDto(training);
    }

    @Override
    public List<Training> getWeeklyTrainings(TrainingDiary trainingDiary) {
        LocalDate today = LocalDate.now();
        int isoWeek = today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        return trainingRepository.findAllByTrainingDiaryAndWeek(trainingDiary, isoWeek);
    }

    @Override
    public List<Training> getLast8WeeksTrainings(TrainingDiary trainingDiary) {
        LocalDate today = LocalDate.now();
        int currentWeek = today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        List<Integer> last8Weeks = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            last8Weeks.add(currentWeek - i);
        }
        return trainingRepository.findAllByTrainingDiaryAndWeekInOrderByWeekDescDayAsc(trainingDiary, last8Weeks);
    }

    private Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(TRAINING_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
    }

    private TrainingDiary getTrainingDiaryById(Long id) {
        return trainingDiaryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(TRAINING_DIARY_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
    }
}
