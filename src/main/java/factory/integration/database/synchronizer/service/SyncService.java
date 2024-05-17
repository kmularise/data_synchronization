package factory.integration.database.synchronizer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import factory.integration.database.synchronizer.mapper.target.TargetTableMapper;
import factory.integration.database.synchronizer.scheduler.job.SyncTaskInfoRequest;
import factory.integration.database.synchronizer.web.service.SourceTableInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SyncService {
	private final SourceTableMapper sourceTableMapper;
	private final TargetTableMapper targetTableMapper;
	private final SourceTableInfoService sourceTableInfoService;

	@Transactional(transactionManager = "distributedTransactionManager", rollbackFor = {Exception.class})
	public void synchronize(SyncTaskInfoRequest syncTaskInfoRequest) {
		sourceTableInfoService.checkTable(syncTaskInfoRequest.getTableName());
		synchronizeTable(syncTaskInfoRequest);
	}

	private void synchronizeTable(SyncTaskInfoRequest syncTaskInfoRequest) {
		String tableName = syncTaskInfoRequest.getTableName();
		String temporaryTable = getTemporaryTable(tableName);
		targetTableMapper.deleteAll(temporaryTable);
		List<Map<String, Object>> rows = sourceTableMapper.selectAllFromTable(tableName);
		List<String> columnNames = sourceTableInfoService.getIncludedColumns(tableName,
			syncTaskInfoRequest.getExcludedColumns());
		if (rows != null && !rows.isEmpty()) {
			targetTableMapper.insertAll(temporaryTable, rows);
		}
		if (syncTaskInfoRequest.getInsertFlag()) {
			targetTableMapper.insertTableFromTemporaryTable(tableName, temporaryTable, columnNames, "id");
		}
		if (syncTaskInfoRequest.getUpdateFlag()) {
			targetTableMapper.updateTableFromTemporaryTable(tableName, temporaryTable, columnNames, "id");
		}
		if (syncTaskInfoRequest.getDeleteFlag()) {
			targetTableMapper.deleteFromTableNotInTemporaryTable(tableName, temporaryTable, "id");
		}
	}

	private static String getTemporaryTable(String tableName) {
		return "IF_" + tableName;
	}

}
