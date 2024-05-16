package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLog;
import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLogDaoMapper;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskLogService {
	private final SyncTaskLogDaoMapper syncTaskLogDaoMapper;

	public List<SyncTaskLog> selectPage(PageRequestDto pageRequestDto) {
		return syncTaskLogDaoMapper.selectPage(pageRequestDto.getFirst(), pageRequestDto.getSize());
	}
}
