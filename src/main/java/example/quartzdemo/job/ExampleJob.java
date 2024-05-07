package example.quartzdemo.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
@PersistJobDataAfterExecution

public class ExampleJob extends QuartzJobBean {
    public static final String COUNT_KEY = "count";

    @Override
    protected void executeInternal(JobExecutionContext context)  {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int count = dataMap.getInt(COUNT_KEY);
        count++;
        dataMap.put(COUNT_KEY, count);
        System.out.println("Job executed. Count: " + count);
    }
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = JobBuilder.newJob(ExampleJob.class)
                .withIdentity("counterJob")
                .usingJobData(ExampleJob.COUNT_KEY, 0)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("counterTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();

        // Pause job after 5 seconds
        Thread.sleep(5000);
        scheduler.pauseJob(job.getKey());
        System.out.println("Job paused.");

        // Resume job after 10 seconds
        Thread.sleep(5000);
        scheduler.resumeJob(job.getKey());
        System.out.println("Job resumed.");

        // Delete and recreate job after 15 seconds
        Thread.sleep(5000);
        scheduler.deleteJob(job.getKey());
        System.out.println("Job deleted and recreated.");
        job = JobBuilder.newJob(ExampleJob.class)
                .withIdentity("counterJob")
                .usingJobData(ExampleJob.COUNT_KEY, 0)
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
