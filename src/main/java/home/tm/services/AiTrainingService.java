package home.tm.services;

import home.tm.TimeKeeper.api.models.WeeklyTrainingRequest;
import home.tm.TimeKeeper.api.models.WeeklyTrainingSummary;

public interface AiTrainingService {
    WeeklyTrainingSummary analyze(WeeklyTrainingRequest dto);
}
