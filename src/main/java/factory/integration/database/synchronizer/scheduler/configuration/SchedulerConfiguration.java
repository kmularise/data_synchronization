package factory.integration.database.synchronizer.scheduler.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import factory.integration.database.synchronizer.scheduler.job.SynchronizationJob;

@Configuration
public class SchedulerConfiguration {
	@Bean
	public JobDetail synchronizationJobDetail() {
		return JobBuilder.newJob(SynchronizationJob.class)
			.withIdentity("synchronization job")
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger synchronizationJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
			.withIntervalInMinutes(1)
			.repeatForever();

		return TriggerBuilder.newTrigger()
			.forJob(synchronizationJobDetail())
			.withIdentity("synchronization trigger")
			.withSchedule(scheduleBuilder)
			.build();
	}
}
