package home.tm.quartz;

import home.tm.services.ScheduledService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeeklyTrainingEvaluationJob extends QuartzJobBean {

    private final ScheduledService scheduledService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        scheduledService.trainingAnalyze();
    }
}
