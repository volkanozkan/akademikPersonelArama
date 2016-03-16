package quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import serviceAndJOB.PersonelUploadTODatabaseJOB;

@WebListener
public class QuartzRunner implements javax.servlet.ServletContextListener
{
	public void run()
	{
		try
		{
			JobDetail job1 = JobBuilder.newJob(PersonelUploadTODatabaseJOB.class).withIdentity("job1", "group1")
					.build();

			Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1")
					// her ayın 1 i saat : 00.30
					.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0 30 0 1 1/1 ? *"))).build();

			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		System.out.println("Zamanlayıcı Sonlandırıldı");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		System.out.println("Zamanlayıcı Başladı");
		run();
	}
}
