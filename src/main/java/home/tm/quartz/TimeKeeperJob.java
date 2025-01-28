package home.tm.quartz;

import home.tm.services.TimeKeeperJobCronService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeKeeperJob extends QuartzJobBean {

    private final TimeKeeperJobCronService timeKeeperJobCronService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        timeKeeperJobCronService.checkValidation(context);
    }
}
