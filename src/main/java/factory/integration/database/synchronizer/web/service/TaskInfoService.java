package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTask;
import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskDaoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskInfoService {
	private final SyncTaskDaoMapper syncTaskDaoMapper;

	@Transactional(transactionManager = "schedulerTransactionManager")
	public List<SyncTask> getTaskPage(int page, int size) {
		Long first = (long)(page - 1) * size;
		return syncTaskDaoMapper.selectPage(first, size);
	}

	@Transactional(transactionManager = "schedulerTransactionManager")
	public Long getTotalPage(int size) {
		return syncTaskDaoMapper.count() / size + 1;
	}
}
