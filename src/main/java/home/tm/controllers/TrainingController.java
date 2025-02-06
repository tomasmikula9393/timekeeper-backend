package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.TrainingsApi;
import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.TimeKeeper.api.models.TrainingPaginatedListDto;
import home.tm.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ws/rest")
@RequiredArgsConstructor
public class TrainingController implements TrainingsApi {

    private final TrainingService trainingService;

    @Override
    public ResponseEntity<TrainingDto> createTraining(Long diaryId, TrainingDto dto) {
        return new ResponseEntity<>(trainingService.createTraining(diaryId, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTraining(Long diaryId, Long trainingId) {
        trainingService.deleteTraining(diaryId, trainingId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TrainingDto> getTraining(Long diaryId, Long trainingId) {
        return new ResponseEntity<>(trainingService.getTraining(diaryId, trainingId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainingDto>> getTrainings(Long diaryId, Pageable pageable, String search) {
        return new ResponseEntity<>(trainingService.getTrainings(diaryId, pageable, search), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TrainingDto> updateTraining(Long diaryId, Long trainingId, TrainingDto dto) {
        return new ResponseEntity<>(trainingService.updateTraining(diaryId, trainingId, dto), HttpStatus.OK);
    }
}
