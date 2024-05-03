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

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskDaoMapper;
import factory.integration.database.synchronizer.mapper.scheduler.TaskInfoDto;
import factory.integration.database.synchronizer.scheduler.job.SyncJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SchedulerExecutionService {
	private final SyncTaskDaoMapper syncTaskDaoMapper;
	private final Scheduler scheduler;

	public void initAllSchedulers() {
		List<TaskInfoDto> taskInfoDtos = syncTaskDaoMapper.selectAll();
		for (TaskInfoDto taskInfoDto : taskInfoDtos) {
			if (!taskInfoDto.isActive()) {
				return;
			}
			JobDetail jobDetail = buildJobDetail(taskInfoDto);
			Trigger trigger = buildTrigger(jobDetail, taskInfoDto);
			try {
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				log.info("job enrollment need retry : {}", taskInfoDto);
			}
			log.info("job enrollment success!: {}", taskInfoDto);
		}
	}

	private JobDetail buildJobDetail(TaskInfoDto taskInfoDto) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(SyncJob.TABLE_NAME, taskInfoDto.getTableName());
		jobDataMap.put(SyncJob.JOB_ID, taskInfoDto.getId());
		jobDataMap.put(SyncJob.EXCLUDED_COLUMNS, taskInfoDto.getExcludedColumns());
		jobDataMap.put(SyncJob.UPDATE_FLAG, taskInfoDto.getUpdateFlag());
		jobDataMap.put(SyncJob.DELETE_FLAG, taskInfoDto.getDeleteFlag());
		jobDataMap.put(SyncJob.INSERT_FLAG, taskInfoDto.getInsertFlag());
		return JobBuilder.newJob(SyncJob.class)
			.usingJobData(jobDataMap)
			.build();
	}

	private Trigger buildTrigger(JobDetail jobDetail, TaskInfoDto taskInfoDto) {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
			.withIntervalInMinutes(taskInfoDto.getPeriodMinutes())
			.repeatForever();
		return TriggerBuilder.newTrigger()
			.forJob(jobDetail)
			.withSchedule(scheduleBuilder)
			.build();
	}
}
