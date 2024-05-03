package example.quartzdemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzExample {
    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1")
                .usingJobData("count",0)
                .build();

        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1)
                .repeatForever();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1")
                .startNow()
                .withSchedule(simpleScheduleBuilder)
                .build();

        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();

        scheduler.scheduleJob(job,trigger);
    }
}
