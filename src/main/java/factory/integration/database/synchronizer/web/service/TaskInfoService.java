package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskDaoMapper;
import factory.integration.database.synchronizer.mapper.scheduler.TaskInfoDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskInfoService {
	private final SyncTaskDaoMapper syncTaskDaoMapper;

	@Transactional(transactionManager = "schedulerTransactionManager")
	public List<TaskInfoDto> getAllTask() {
		return syncTaskDaoMapper.selectAll();
	}
}
