package factory.integration.database.synchronizer.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.source.ColumnInfoMapper;
import factory.integration.database.synchronizer.mapper.target.TargetTableMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TargetDataService {
	private final TargetTableMapper targetTableMapper;
	private final ColumnInfoMapper columnInfoMapper;

	@Transactional(transactionManager = "targetTransactionManager", readOnly = true)
	public List<String> getTableColumns(String tableName) {
		return columnInfoMapper.selectColumnsByTable(tableName, "target_schema");
	}

	@Transactional(transactionManager = "targetTransactionManager", readOnly = true)
	public List<String> getAllTables() {
		return columnInfoMapper.selectAllTableNames("target_schema");
	}

	@Transactional(transactionManager = "targetTransactionManager")
	public List<Map<String, Object>> selectAll(String tableName) {
		return targetTableMapper.selectAllFromTable(tableName);
	}
}
