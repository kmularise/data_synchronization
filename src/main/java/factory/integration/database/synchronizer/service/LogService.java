package factory.integration.database.synchronizer.service;

import org.springframework.stereotype.Service;

import factory.integration.database.synchronizer.mapper.scheduler.SchedulerLogDaoMapper;
import factory.integration.database.synchronizer.mapper.scheduler.TaskDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
	private final SchedulerLogDaoMapper schedulerLogDaoMapper;

	public void createSuccess(TaskDto taskDto) {
		schedulerLogDaoMapper.insertLog(taskDto);
	}

	public void createFailure(TaskDto taskDto) {
		schedulerLogDaoMapper.insertLog(taskDto);
	}
}
