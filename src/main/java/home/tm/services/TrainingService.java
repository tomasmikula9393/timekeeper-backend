package home.tm.services;

import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingService {

    TrainingDto createTraining(Long diaryId, TrainingDto dto);

    void deleteTraining(Long diaryId, Long id);

    TrainingDto getTraining(Long diaryId, Long id);

    List<TrainingDto> getTrainings(Long diaryId, Pageable pageable, String search);

    TrainingDto updateTraining(Long diaryId, Long id, TrainingDto dto);

    List<Training> getWeeklyTrainings(TrainingDiary trainingDiary);

    List<Training> getLast8WeeksTrainings(TrainingDiary trainingDiaryById);
}
