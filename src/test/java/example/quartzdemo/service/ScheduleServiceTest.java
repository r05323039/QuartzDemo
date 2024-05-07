package example.quartzdemo.service;

import example.quartzdemo.job.ExampleJob;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceTest {
    @Autowired
    ScheduleService sut;

    @Autowired
    Scheduler scheduler;

    @AfterEach
    void tearDown() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void run() throws SchedulerException {
        sut.create(ExampleJob.class, "ExampleJobTest", "* * * * * ? *");


        System.out.println(scheduler.getSchedulerName());

        System.out.println(scheduler);

        System.out.println(scheduler.checkExists(new JobKey("ExampleJobTest", "RegularScheduler")));

    }

    @Test
    void pause()   {
        try {
            sut.create(ExampleJob.class, "counter", "* * * * * ? *");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

//        scheduler.start();
    }
}