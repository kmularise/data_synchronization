package factory.integration.database.synchronizer.test.unittest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zaxxer.hikari.HikariConfig;

import factory.integration.database.synchronizer.mapper.source.SourceColumnInfoMapper;
import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.service.SourceTableInfoService;

@ExtendWith(MockitoExtension.class)
public class SourceTableInfoServiceTest {

	private SourceTableInfoService sourceTableInfoService;
	private SourceColumnInfoMapper sourceColumnInfoMapper;

	private HikariConfig sourceHikariConfig;

	@BeforeEach
	void init() {
		sourceColumnInfoMapper = mock(SourceColumnInfoMapper.class);
		sourceHikariConfig = mock(HikariConfig.class);
		sourceTableInfoService = new SourceTableInfoService(sourceColumnInfoMapper, sourceHikariConfig);
	}

	@Test
	@DisplayName("유효하지 않은 테이블 이름이 입력값으로 들어오면 예외를 반환한다.")
	void checkTableInvalid() {
		String schema = "source_schema";
		doReturn(schema).when(sourceHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(sourceColumnInfoMapper).selectAllTableNames(schema);
		assertThrows(BusinessException.class, () -> sourceTableInfoService.checkTable("invalid_table_name"));
	}

	@Test
	@DisplayName("유효한 테이블 이름이 입력값으로 들어오면 테이블 이름을 확인한다.")
	void checkTableValid() {
		String schema = "source_schema";
		doReturn(schema).when(sourceHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(sourceColumnInfoMapper).selectAllTableNames(schema);
		Assertions.assertDoesNotThrow(() -> sourceTableInfoService.checkTable("customer"));
	}

	@Test
	@DisplayName("테이블의 컬럼 필드 이름을 찾아올 때, 유효한 테이블이면 테이블 컬럼 필드 이름을 반환한다.")
	void getColumnsWithValidTable() {
		String tableName = "customer";
		String schema = "source_schema";
		doReturn(schema).when(sourceHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(sourceColumnInfoMapper).selectAllTableNames(schema);
		doReturn(List.of("id", "name")).when(sourceColumnInfoMapper).selectColumnsByTable(tableName, schema);
		assertEquals(List.of("id", "name"), sourceTableInfoService.getColumns(tableName));
	}

	@Test
	@DisplayName("테이블의 컬럼 필드 이름을 찾아올 때, 유효하지 않은 테이블이면 예외를 반환한다.")
	void throwExceptionWtihInvalidTable() {
		String tableName = "sample";
		String schema = "source_schema";
		doReturn(schema).when(sourceHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(sourceColumnInfoMapper).selectAllTableNames(schema);
		assertThrows(BusinessException.class, () -> sourceTableInfoService.getColumns(tableName));
	}

	@Test
	@DisplayName("동기화해야하는 대상 컬럼들을 찾아올 때, 유효하지 않은 컬럼 이름이면 예외를 반환한다.")
	void throwExceptionWithInvalidColumn() {
		String tableName = "customer";
		String schema = "source_schema";
		doReturn(schema).when(sourceHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(sourceColumnInfoMapper).selectAllTableNames(schema);
		doReturn(List.of("id", "name")).when(sourceColumnInfoMapper).selectColumnsByTable(tableName, schema);
		assertThrows(BusinessException.class,
			() -> sourceTableInfoService.getIncludedColumns(tableName, List.of("invalid", "id")));
	}

	@Test
	@DisplayName("동기화해야하는 대상 컬럼들을 찾아올 때, 모두 유효한 컬럼이면 포함시키는 컬럼을 반환한다.")
	void getIncludedColumnsWithValidExcludedColumns() {
		String tableName = "customer";
		String schema = "source_schema";
		doReturn(schema).when(sourceHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(sourceColumnInfoMapper).selectAllTableNames(schema);
		doReturn(List.of("id", "name")).when(sourceColumnInfoMapper).selectColumnsByTable(tableName, schema);
		assertEquals(List.of("id"), sourceTableInfoService.getIncludedColumns(tableName, List.of("name")));
	}
}
