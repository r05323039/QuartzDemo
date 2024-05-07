package example.quartzdemo;

import org.quartz.*;

//@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SimpleJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int count = dataMap.getInt("count");
        count++;
        System.out.println("SimpleJob: " + count);
        dataMap.put("count", count);
    }
}
