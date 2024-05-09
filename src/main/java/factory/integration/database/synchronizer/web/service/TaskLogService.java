package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLog;
import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLogDaoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskLogService {
	private final SyncTaskLogDaoMapper syncTaskLogDaoMapper;

	public List<SyncTaskLog> selectPage(int page, int size) {
		Long first = (long)(page - 1) * size;
		return syncTaskLogDaoMapper.selectPage(first, size);
	}
}
