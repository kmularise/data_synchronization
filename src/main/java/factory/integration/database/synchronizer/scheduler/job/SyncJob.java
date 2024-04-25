package factory.integration.database.synchronizer.scheduler.job;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import factory.integration.database.synchronizer.mapper.scheduler.Status;
import factory.integration.database.synchronizer.mapper.scheduler.TaskDto;
import factory.integration.database.synchronizer.service.LogService;
import factory.integration.database.synchronizer.service.SyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SyncJob implements Job {
	public static final String TABLE_NAME = "table_name";
	public static final String EXCLUDED_COLUMNS = "excluded_columns";
	public static final String JOB_ID = "sync_task_id";
	public static final String HAS_INSERTED = "has_inserted";
	public static final String HAS_UPDATED = "has_updated";

	public static final String HAS_DELETED = "has_deleted";

	private final SyncService syncService;
	private final LogService logService;

	@Override
	public void execute(JobExecutionContext context) {
		JobDataMap dataMap = context.getMergedJobDataMap();
		String tableName = dataMap.getString(TABLE_NAME);
		Long jobId = dataMap.getLong(JOB_ID);
		boolean hasInserted = dataMap.getBoolean(HAS_INSERTED);
		boolean hasDeleted = dataMap.getBoolean(HAS_DELETED);
		boolean hasUpdated = dataMap.getBoolean(HAS_UPDATED);
		//작업을 생성하는 곳(SchedulerExecutionService)에서 List<String>형으로 값을 넣었으므로 올바른 형변환입니다.
		@SuppressWarnings("unchecked")
		List<String> excludedColumns = (List<String>)dataMap.get(EXCLUDED_COLUMNS);
		SyncTableInfo syncTableInfo = new SyncTableInfo(tableName, hasInserted, hasDeleted, hasUpdated,
			excludedColumns);
		LocalDateTime startDateTime = LocalDateTime.now();
		try {
			syncService.synchronize(syncTableInfo);
			LocalDateTime endDateTime = LocalDateTime.now();
			TaskDto taskDto = new TaskDto(jobId, startDateTime, endDateTime, Status.SUCCESS);
			logService.createSuccess(taskDto);
		} catch (Exception exception) {
			LocalDateTime endDateTime = LocalDateTime.now();
			TaskDto taskDto = new TaskDto(jobId, startDateTime, endDateTime, Status.FAILURE);
			logService.createFailure(taskDto);
		}
	}
}
