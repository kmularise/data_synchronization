package factory.integration.database.synchronizer.test.unittest.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import factory.integration.database.synchronizer.mapper.target.TargetTableMapper;
import factory.integration.database.synchronizer.scheduler.job.SyncTaskInfoRequest;
import factory.integration.database.synchronizer.service.SyncService;
import factory.integration.database.synchronizer.web.service.SourceTableInfoService;

@ExtendWith(MockitoExtension.class)
public class SyncServiceTest {
	private SyncService syncService;
	private SourceTableMapper sourceTableMapper;
	private TargetTableMapper targetTableMapper;
	private SourceTableInfoService sourceTableInfoService;

	@BeforeEach
	void init() {
		sourceTableMapper = mock(SourceTableMapper.class);
		targetTableMapper = mock(TargetTableMapper.class);
		sourceTableInfoService = mock(SourceTableInfoService.class);
		syncService = new SyncService(sourceTableMapper, targetTableMapper, sourceTableInfoService);
	}

	@ParameterizedTest
	@CsvSource({
		"true, true, true, 1, 1, 1",
		"false, true, true, 0, 1, 1",
		"true, false, true, 1, 0, 1",
		"true, true, false, 1, 1, 0",
		"true, false, false, 1, 0, 0",
		"false, true, false, 0, 1, 0",
		"false, false, false, 0, 0, 0",
	})
	@DisplayName("insert Flag, update Flag, delete Flag에 따른 동기화 로직 호출을 검증합니다.")
	void synchronize(boolean insertFlag, boolean updateFlag, boolean deleteFlag,
		int insertTime, int updateTime, int deleteTime) {
		String tableName = "customers";
		String temporaryTable = "IF_customers";
		List<String> columns = List.of("id", "name");
		List<Map<String, Object>> rows = getData(10);
		SyncTaskInfoRequest syncTaskInfoRequest = new SyncTaskInfoRequest(1L,
			tableName, insertFlag, deleteFlag, updateFlag, new ArrayList<>());
		doReturn(rows).when(sourceTableMapper).selectAllFromTable(tableName);
		doReturn(columns).when(sourceTableInfoService)
			.getIncludedColumns(tableName, syncTaskInfoRequest.getExcludedColumns());
		syncService.synchronize(syncTaskInfoRequest);
		verify(targetTableMapper, times(1)).deleteAll(temporaryTable);
		verify(targetTableMapper, times(1)).insertAll(temporaryTable, rows);
		verify(targetTableMapper, times(insertTime)).insertTableFromTemporaryTable(tableName, temporaryTable, columns,
			"id");
		verify(targetTableMapper, times(updateTime)).updateTableFromTemporaryTable(tableName, temporaryTable, columns,
			"id");
		verify(targetTableMapper, times(deleteTime)).deleteFromTableNotInTemporaryTable(tableName, temporaryTable,
			"id");
	}

	private List<Map<String, Object>> getData(int n) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", (long)i);
			map.put("name", "test" + i);
			dataList.add(map);
		}
		return dataList;
	}
}
