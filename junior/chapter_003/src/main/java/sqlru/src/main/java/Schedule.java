import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class Schedule {

    public static void main(String[] args) {
		try {
			JobDetailImpl job = new JobDetailImpl();
			job.setName("Sqlru application");
			job.setJobClass(Application.class);

			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setName("cronTrigger");
			trigger.setCronExpression("0 0 12 * * ?");

			//Execute the job.
			Scheduler scheduler =
					new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
