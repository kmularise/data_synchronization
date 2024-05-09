package factory.integration.database.synchronizer.scheduler.job;

import static factory.integration.database.synchronizer.scheduler.job.JobConst.*;

import java.time.LocalDateTime;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLog;
import factory.integration.database.synchronizer.mapper.scheduler.TaskStatus;
import factory.integration.database.synchronizer.service.SyncService;
import factory.integration.database.synchronizer.service.TaskLogWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SyncJob implements Job {

	private final SyncService syncService;
	private final TaskLogWriteService taskLogWriteService;

	@Override
	public void execute(JobExecutionContext context) {
		JobDataMap dataMap = context.getMergedJobDataMap();
		SyncTaskInfoRequest syncTaskInfoRequest = (SyncTaskInfoRequest)dataMap.get(SYNC_TASK_INFO);
		//AOP
		SyncTaskLog syncTaskLog = null;
		try {
			syncTaskLog = new SyncTaskLog(syncTaskInfoRequest.getJobId(),
				LocalDateTime.now(),
				TaskStatus.WAITING);
			taskLogWriteService.createLog(syncTaskLog);
			syncService.synchronize(syncTaskInfoRequest);
			taskLogWriteService.updateSuccess(syncTaskLog.getId());
		} catch (Exception exception) {
			if (syncTaskLog == null || syncTaskLog.getId() == null) {
				taskLogWriteService.createLog(new SyncTaskLog(syncTaskInfoRequest.getJobId(),
					LocalDateTime.now(),
					TaskStatus.FAILURE));
				return;
			}
			taskLogWriteService.updateFailure(syncTaskLog.getId(), syncTaskLog.getSyncTaskId());
		}
	}
}
