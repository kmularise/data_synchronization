package factory.integration.database.synchronizer.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLog;
import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLogDaoMapper;
import factory.integration.database.synchronizer.mapper.scheduler.TaskStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskLogWriteService {
	private final SyncTaskLogDaoMapper syncTaskLogDaoMapper;

	public void createLog(SyncTaskLog syncTaskLog) {
		syncTaskLogDaoMapper.insertLog(syncTaskLog);
	}

	public void updateSuccess(Long id) {
		syncTaskLogDaoMapper.updateLog(id, TaskStatus.SUCCESS, LocalDateTime.now());
	}

	public void updateFailure(Long id, Long syncTaskId) {
		if (id == null) {
			syncTaskLogDaoMapper.insertLog(new SyncTaskLog(syncTaskId, LocalDateTime.now(), TaskStatus.FAILURE));
			return;
		}
		syncTaskLogDaoMapper.updateLog(id, TaskStatus.FAILURE, LocalDateTime.now());
	}
}
