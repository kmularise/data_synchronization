package factory.integration.database.synchronizer.service;

import static factory.integration.database.synchronizer.scheduler.job.JobConst.*;

import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTask;
import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskDaoMapper;
import factory.integration.database.synchronizer.scheduler.job.SyncJob;
import factory.integration.database.synchronizer.scheduler.job.SyncTaskInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SchedulerExecutionService {
	private final SyncTaskDaoMapper syncTaskDaoMapper;
	private final Scheduler scheduler;

	public void enrollAllTasks() {
		List<SyncTask> syncTasks = syncTaskDaoMapper.selectAll();
		for (SyncTask syncTask : syncTasks) {
			if (!syncTask.isActive()) {
				return;
			}
			JobDetail jobDetail = buildJobDetail(syncTask);
			Trigger trigger = buildTrigger(jobDetail, syncTask);
			try {
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				log.info("job enrollment need retry : {}", syncTask);
			}
			log.info("job enrollment success!: {}", syncTask);
		}
	}

	private JobDetail buildJobDetail(SyncTask syncTask) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(SYNC_TASK_INFO, new SyncTaskInfoRequest(syncTask));
		return JobBuilder.newJob(SyncJob.class)
			.usingJobData(jobDataMap)
			.build();
	}

	private Trigger buildTrigger(JobDetail jobDetail, SyncTask syncTask) {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
			.withIntervalInMinutes(syncTask.getPeriodMinutes())
			.repeatForever();
		return TriggerBuilder.newTrigger()
			.forJob(jobDetail)
			.withSchedule(scheduleBuilder)
			.build();
	}
}
