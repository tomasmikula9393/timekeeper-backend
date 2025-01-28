package home.tm.services;

import org.quartz.JobExecutionContext;

public interface TimeKeeperJobCronService {

    void checkValidation(JobExecutionContext context);
}
