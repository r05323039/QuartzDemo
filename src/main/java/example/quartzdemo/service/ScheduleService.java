package example.quartzdemo.service;

import example.quartzdemo.job.ExampleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private Scheduler scheduler;

    @EventListener(ApplicationStartedEvent.class)
    public void jobInitializer() throws SchedulerException {
        String jobName = "ExampleJobTest";
        JobKey key = new JobKey(jobName, "RegularScheduler");

        deleteJobIfExists(key);

        String cronExpression = "* * * * * ? *";
        cronScheduler(ExampleJob.class, jobName, cronExpression);

    }

    private void deleteJobIfExists(JobKey key) throws SchedulerException {
        if (scheduler.checkExists(key)) {
            scheduler.deleteJob(key);
        }
    }

    private <T> void cronScheduler(Class<? extends Job> jobClass, String jobName, String cronExpression) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, "RegularScheduler")
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobName, "RegularScheduler")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
