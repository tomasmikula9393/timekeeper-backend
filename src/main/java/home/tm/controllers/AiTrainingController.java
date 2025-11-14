package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.AiTrainingApi;
import home.tm.TimeKeeper.api.models.WeeklyTrainingRequest;
import home.tm.TimeKeeper.api.models.WeeklyTrainingSummary;
import home.tm.services.AiTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/rest/")
@RequiredArgsConstructor
public class AiTrainingController implements AiTrainingApi {

    private final AiTrainingService service;

    @Override
    public ResponseEntity<WeeklyTrainingSummary> apiAiTrainingWeeklySummaryPost(WeeklyTrainingRequest weeklyTrainingRequest) {
        return AiTrainingApi.super.apiAiTrainingWeeklySummaryPost(weeklyTrainingRequest);
    }
}