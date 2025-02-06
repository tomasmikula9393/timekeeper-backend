package home.tm.services;

import home.tm.TimeKeeper.api.models.TrainingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingService {

    TrainingDto createTraining(Long diaryId, TrainingDto dto);

    void deleteTraining(Long diaryId, Long id);

    TrainingDto getTraining(Long diaryId, Long id);

    List<TrainingDto> getTrainings(Long diaryId, Pageable pageable, String search);

    TrainingDto updateTraining(Long diaryId, Long id, TrainingDto dto);
}
