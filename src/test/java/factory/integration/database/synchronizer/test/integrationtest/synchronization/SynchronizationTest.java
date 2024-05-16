package factory.integration.database.synchronizer.test.integrationtest.synchronization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import factory.integration.database.synchronizer.mapper.source.ColumnInfoMapper;
import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import factory.integration.database.synchronizer.mapper.target.TargetTableMapper;
import factory.integration.database.synchronizer.scheduler.job.SyncTaskInfoRequest;
import factory.integration.database.synchronizer.service.SyncService;
import factory.integration.database.synchronizer.test.integrationtest.IntegrationTestBase;

public class SynchronizationTest extends IntegrationTestBase {
	@Autowired
	private SyncService syncService;
	@Autowired
	private SourceTableMapper sourceTableMapper;
	@Autowired
	private TargetTableMapper targetTableMapper;

	@Autowired
	private ColumnInfoMapper columnInfoMapper;

	@BeforeEach
	public void setUp() {
		List<String> tableNames = columnInfoMapper.selectAllTableNames("source_schema");
		for (String tableName : tableNames) {
			sourceTableMapper.truncateTable(tableName);
			targetTableMapper.truncateTable(tableName);
			targetTableMapper.truncateTable("IF_" + tableName);
		}
	}

	@DisplayName("source 테이블의 insert 작업과 관련하여 동기화를 진행할 때, source 테이블과 target 테이블의 데이터가 동기화됩니다.")
	@Test
	public void testInsert() {
		insertCustomers(10);
		syncService.synchronize(new SyncTaskInfoRequest(1L, "customers", true, false, false,
			new ArrayList<>()));
		List<Map<String, Object>> sourceList = sourceTableMapper.selectAllFromTable("customers");
		List<Map<String, Object>> targetList = targetTableMapper.selectAllFromTable("customers");
		Assertions.assertEquals(sourceList, targetList);
	}

	@DisplayName("source 테이블의 update 작업과 관련하여 동기화를 진행할 때, source 테이블과 target 테이블의 데이터가 동기화됩니다.")
	@Test
	public void testUpdate() {
		insertCustomers(10);
		List<Map<String, Object>> rows = sourceTableMapper.selectAllFromTable("customers");
		targetTableMapper.insertAll("customers", rows);
		updateCustomers(rows);
		syncService.synchronize(new SyncTaskInfoRequest(1L, "customers", false, false, true,
			new ArrayList<>()));
		List<Map<String, Object>> sourceList = sourceTableMapper.selectAllFromTable("customers");
		List<Map<String, Object>> targetList = targetTableMapper.selectAllFromTable("customers");
		Assertions.assertEquals(sourceList, targetList);
	}

	@DisplayName("source 테이블의 delete 작업과 관련하여 동기화를 진행할 때, source 테이블과 target 테이블의 데이터가 동기화됩니다.")
	@Test
	public void testDelete() {
		insertCustomers(10);
		List<Map<String, Object>> rows = sourceTableMapper.selectAllFromTable("customers");
		targetTableMapper.insertAll("customers", rows);
		deleteCustomers(5, rows);
		syncService.synchronize(new SyncTaskInfoRequest(1L, "customers", false, true, false,
			new ArrayList<>()));
		List<Map<String, Object>> sourceList = sourceTableMapper.selectAllFromTable("customers");
		List<Map<String, Object>> targetList = targetTableMapper.selectAllFromTable("customers");
		Assertions.assertEquals(sourceList, targetList);
	}

	private void insertCustomers(int size) {
		List<Map<String, Object>> rows = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Map<String, Object> row = new HashMap<>();
			row.put("name", "test" + i);
			rows.add(row);
		}
		sourceTableMapper.insertAll("customers", rows);
	}

	private void updateCustomers(List<Map<String, Object>> rows) {
		for (Map<String, Object> row : rows) {
			row.put("name", "test");
			sourceTableMapper.updateByKey("customers", "id", row);
		}
	}

	private void deleteCustomers(int size, List<Map<String, Object>> rows) {
		for (int i = 0; i < Math.min(size, rows.size()); i++) {
			sourceTableMapper.deleteByKey("customers", "id", rows.get(i));
		}
	}
}
