package factory.integration.database.synchronizer.service;

import org.springframework.stereotype.Service;

import factory.integration.database.synchronizer.mapper.scheduler.SchedulerLogDaoMapper;
import factory.integration.database.synchronizer.mapper.scheduler.TaskLogDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
	private final SchedulerLogDaoMapper schedulerLogDaoMapper;

	public void createSuccess(TaskLogDto taskLogDto) {
		schedulerLogDaoMapper.insertLog(taskLogDto);
	}

	public void createFailure(TaskLogDto taskLogDto) {
		schedulerLogDaoMapper.insertLog(taskLogDto);
	}
}
