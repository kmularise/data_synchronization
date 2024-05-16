package factory.integration.database.synchronizer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.source.ColumnInfoMapper;
import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import factory.integration.database.synchronizer.mapper.target.TargetTableMapper;
import factory.integration.database.synchronizer.scheduler.job.SyncTaskInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SyncService {
	private final SourceTableMapper sourceTableMapper;
	private final ColumnInfoMapper columnInfoMapper;
	private final TargetTableMapper targetTableMapper;

	@Transactional(transactionManager = "distributedTransactionManager", rollbackFor = {Exception.class})
	public void synchronize(SyncTaskInfoRequest syncTaskInfoRequest) {
		List<String> tables = columnInfoMapper.selectAllTableNames("source_schema");
		for (String table : tables) {
			if (table.equals(syncTaskInfoRequest.getTableName())) {
				synchronizeTable(syncTaskInfoRequest);
				return;
			}
		}
		throw new IllegalArgumentException("invalid table name");
	}

	private void synchronizeTable(SyncTaskInfoRequest syncTaskInfoRequest) {
		String tableName = syncTaskInfoRequest.getTableName();
		String temporaryTable = getTemporaryTable(tableName);
		targetTableMapper.deleteAll(temporaryTable);
		List<Map<String, Object>> rows = sourceTableMapper.selectAllFromTable(tableName);
		List<String> columnNames = columnInfoMapper.selectColumnsByTable(tableName, "source_schema");
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
