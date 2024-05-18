package factory.integration.database.synchronizer.test.unittest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zaxxer.hikari.HikariConfig;

import factory.integration.database.synchronizer.mapper.target.TargetColumnInfoMapper;
import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.service.TargetTableInfoService;

@ExtendWith(MockitoExtension.class)
public class TargetTableInfoServiceTest {
	private TargetTableInfoService targetTableInfoService;
	private HikariConfig targetHikariConfig;
	private TargetColumnInfoMapper targetColumnInfoMapper;

	@BeforeEach
	void init() {
		targetHikariConfig = mock(HikariConfig.class);
		targetColumnInfoMapper = mock(TargetColumnInfoMapper.class);
		targetTableInfoService = new TargetTableInfoService(targetColumnInfoMapper, targetHikariConfig);
	}

	@Test
	@DisplayName("유효하지 않은 테이블 이름이 입력값으로 들어오면 예외를 반환한다.")
	void checkTableInvalid() {
		String schema = "target_schema";
		doReturn(schema).when(targetHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(targetColumnInfoMapper).selectAllTableNames(schema);
		assertThrows(BusinessException.class, () -> targetTableInfoService.checkTable("invalid_table_name"));
	}

	@Test
	@DisplayName("유효한 테이블 이름이 입력값으로 들어오면 테이블 이름을 확인한다.")
	void checkTableValid() {
		String schema = "target_schema";
		doReturn(schema).when(targetHikariConfig).getSchema();
		doReturn(List.of("customer", "IF_customer")).when(targetColumnInfoMapper).selectAllTableNames(schema);
		assertDoesNotThrow(() -> targetTableInfoService.checkTable("IF_customer"));
	}

	@Test
	@DisplayName("테이블의 컬럼 필드 이름을 찾아올 때, 유효한 테이블이면 테이블 컬럼 필드 이름을 반환한다.")
	void getColumnsWithValidTable() {
		String tableName = "customer";
		String schema = "target_schema";
		doReturn(schema).when(targetHikariConfig).getSchema();
		doReturn(List.of("customer", "IF_customer")).when(targetColumnInfoMapper).selectAllTableNames(schema);
		doReturn(List.of("id", "name")).when(targetColumnInfoMapper).selectColumnsByTable(tableName, schema);
		assertEquals(List.of("id", "name"), targetTableInfoService.getColumns(tableName));
	}

	@Test
	@DisplayName("테이블의 컬럼 필드 이름을 찾아올 때, 유효하지 않은 테이블이면 예외를 반환한다.")
	void throwExceptionWtihInvalidTable() {
		String tableName = "sample";
		String schema = "source_schema";
		doReturn(schema).when(targetHikariConfig).getSchema();
		doReturn(List.of("customer", "order", "car")).when(targetColumnInfoMapper).selectAllTableNames(schema);
		assertThrows(BusinessException.class, () -> targetTableInfoService.getColumns(tableName));
	}

}
