package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.TrainingDiaryApi;
import home.tm.TimeKeeper.api.models.TrainingDiaryBaseDto;
import home.tm.TimeKeeper.api.models.TrainingDiaryDto;
import home.tm.services.TrainingDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ws/rest")
@RequiredArgsConstructor
public class TrainingDiaryController implements TrainingDiaryApi {

    private final TrainingDiaryService trainingDiaryService;
    
    @Override
    public ResponseEntity<TrainingDiaryDto> createTrainingDiary(TrainingDiaryBaseDto dto) {
        return new ResponseEntity<>(trainingDiaryService.createTrainingDiary(dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTrainingDiary(Long id) {
        trainingDiaryService.deleteTrainingDiary(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TrainingDiaryDto> getTrainingDiary(Long id) {
        return new ResponseEntity<>(trainingDiaryService.getTrainingDiary(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TrainingDiaryDto> updateTrainingDiary(Long id, TrainingDiaryBaseDto dto) {
        return new ResponseEntity<>(trainingDiaryService.updateTrainingDiary(id, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainingDiaryBaseDto>> getTrainingDiaries() {
        return new ResponseEntity<>(trainingDiaryService.getTrainingDiaries(), HttpStatus.OK);
    }
}
