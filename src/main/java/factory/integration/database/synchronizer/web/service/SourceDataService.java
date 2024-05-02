package factory.integration.database.synchronizer.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.source.ColumnInfoMapper;
import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SourceDataService {
	private final SourceTableMapper sourceTableMapper;
	private final ColumnInfoMapper columnInfoMapper;

	@Transactional(transactionManager = "sourceTransactionManager")
	public List<Map<String, Object>> selectAll(String tableName) {
		return sourceTableMapper.selectAllFromTable(tableName);
	}

	@Transactional(transactionManager = "sourceTransactionManager")
	public void insert(String tableName, Map<String, Object> data) {
		sourceTableMapper.insert(tableName, data);
	}

	@Transactional(transactionManager = "sourceTransactionManager")
	public void update(String tableName, Map<String, Object> data) {
		sourceTableMapper.updateByKey(tableName, "id", data);
	}

	@Transactional(transactionManager = "sourceTransactionManager")
	public void delete(String tableName, Map<String, Object> data) {
		sourceTableMapper.deleteByKey(tableName, "id", data);
	}

	@Transactional(transactionManager = "sourceTransactionManager", readOnly = true)
	public List<String> getTableColumns(String tableName) {
		return columnInfoMapper.selectColumnsByTable(tableName, "source_schema");
	}

	@Transactional(transactionManager = "sourceTransactionManager", readOnly = true)
	public List<String> getAllTables() {
		return columnInfoMapper.selectAllTableNames("source_schema");
	}
}
