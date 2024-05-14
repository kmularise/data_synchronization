package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTask;
import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskDaoMapper;
import factory.integration.database.synchronizer.service.SchedulerExecutionService;
import factory.integration.database.synchronizer.web.dto.SyncTaskDto;
import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.exception.ErrorMessage;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskInfoService {
	private final SyncTaskDaoMapper syncTaskDaoMapper;
	private final SourceTableInfoService sourceTableInfoService;
	private final SchedulerExecutionService schedulerExecutionService;

	@Transactional(transactionManager = "schedulerTransactionManager")
	public PageResponseDto<SyncTask> getTaskPage(PageRequestDto pageRequestDto) {
		List<SyncTask> syncTasks = syncTaskDaoMapper.selectPage(pageRequestDto.getFirst(), pageRequestDto.getSize());
		return new PageResponseDto<>(pageRequestDto.getSize(), syncTaskDaoMapper.count(), syncTasks);
	}

	@Transactional(transactionManager = "schedulerTransactionManager")
	public Long getTotalPage(int size) {
		return syncTaskDaoMapper.count() / size + 1;
	}

	//TODO: insert
	@Transactional(transactionManager = "schedulerTransactionManager")
	public void addTask(SyncTaskDto syncTaskDto) {
		if (syncTaskDto.getPeriodMinutes() <= 0) {
			throw new BusinessException(ErrorMessage.PERIOD_NON_POSITIVE, HttpStatus.BAD_REQUEST.value());
		}
		sourceTableInfoService.checkTable(syncTaskDto.getTableName());
		sourceTableInfoService.checkColumns(syncTaskDto.getTableName(), syncTaskDto.getExcludedColumns());
		SyncTask syncTask = new SyncTask(
			syncTaskDto.getJobName(),
			syncTaskDto.getPeriodMinutes(),
			syncTaskDto.getTableName(),
			syncTaskDto.getIsActive(),
			syncTaskDto.getInsertFlag(),
			syncTaskDto.getUpdateFlag(),
			syncTaskDto.getDeleteFlag(),
			syncTaskDto.getExcludedColumns()
		);
		syncTaskDaoMapper.insert(syncTask);
		schedulerExecutionService.enrollTask(syncTask);
	}

	@Transactional(transactionManager = "schedulerTransactionManager")
	public void deleteTask(Long id) {
		schedulerExecutionService.disableTask(id);
		syncTaskDaoMapper.deleteById(id);
	}

	@Transactional(transactionManager = "schedulerTransactionManager")
	public void disableTask(Long id) {
		syncTaskDaoMapper.selectById(id);
		syncTaskDaoMapper.updateActive(id, false);
		schedulerExecutionService.disableTask(id);
	}

	@Transactional(transactionManager = "schedulerTransactionManager")
	public void enableTask(Long id) {
		if (syncTaskDaoMapper.selectById(id) == null) {
			throw new BusinessException(ErrorMessage.NOT_FOUND, HttpStatus.NOT_FOUND.value());
		}
		syncTaskDaoMapper.updateActive(id, true);
		schedulerExecutionService.enableTask(id);
	}
}
