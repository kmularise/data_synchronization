package factory.integration.database.synchronizer.service;

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

import factory.integration.database.synchronizer.mapper.scheduler.SchedulerDaoMapper;
import factory.integration.database.synchronizer.mapper.scheduler.SchedulerDto;
import factory.integration.database.synchronizer.scheduler.job.SyncJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SchedulerExecutionService {
	private final SchedulerDaoMapper schedulerDaoMapper;
	private final Scheduler scheduler;

	public void initAllSchedulers() {
		List<SchedulerDto> schedulerDtos = schedulerDaoMapper.selectAll();
		for (SchedulerDto schedulerDto : schedulerDtos) {
			if (!schedulerDto.isActive()) {
				return;
			}
			JobDetail jobDetail = buildJobDetail(schedulerDto);
			Trigger trigger = buildTrigger(jobDetail, schedulerDto);
			try {
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				log.info("job enrollment need retry : {}", schedulerDto);
			}
			log.info("job enrollment success!: {}", schedulerDto);
		}
	}

	private JobDetail buildJobDetail(SchedulerDto schedulerDto) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(SyncJob.TABLE_NAME, schedulerDto.getTableName());
		jobDataMap.put(SyncJob.JOB_ID, schedulerDto.getId());
		jobDataMap.put(SyncJob.EXCLUDED_COLUMNS, schedulerDto.getExcludedColumns());
		jobDataMap.put(SyncJob.HAS_UPDATED, schedulerDto.getHasUpdated());
		jobDataMap.put(SyncJob.HAS_DELETED, schedulerDto.getHasDeleted());
		jobDataMap.put(SyncJob.HAS_INSERTED, schedulerDto.getHasInserted());
		return JobBuilder.newJob(SyncJob.class)
			.usingJobData(jobDataMap)
			.build();
	}

	private Trigger buildTrigger(JobDetail jobDetail, SchedulerDto schedulerDto) {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
			.withIntervalInMinutes(schedulerDto.getPeriodMinutes())
			.repeatForever();
		return TriggerBuilder.newTrigger()
			.forJob(jobDetail)
			.withSchedule(scheduleBuilder)
			.build();
	}
}
