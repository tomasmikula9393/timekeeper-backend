package home.tm.services;

import home.tm.TimeKeeper.api.models.TrainingDiaryBaseDto;
import home.tm.TimeKeeper.api.models.TrainingDiaryDto;
import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.model.Training;
import home.tm.model.TrainingDiary;

import java.util.List;

public interface TrainingDiaryService {

    TrainingDiaryDto createTrainingDiary(TrainingDiaryBaseDto dto);

    void deleteTrainingDiary(Long id);

    TrainingDiaryDto getTrainingDiary(Long id);

    TrainingDiaryDto updateTrainingDiary(Long id, TrainingDiaryBaseDto dto);

    TrainingDiary getTrainingDiaryById(Long id);

    List<TrainingDiaryBaseDto> getTrainingDiaries();

    List<TrainingDto> getLastWeek(Long userId);

    List<TrainingDto> getLast8Weeks(Long userId);
}
