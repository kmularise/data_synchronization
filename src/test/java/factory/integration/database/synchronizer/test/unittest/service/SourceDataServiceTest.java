package factory.integration.database.synchronizer.test.unittest.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import factory.integration.database.synchronizer.web.service.SourceDataService;
import factory.integration.database.synchronizer.web.service.SourceTableInfoService;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;

@ExtendWith(MockitoExtension.class)
public class SourceDataServiceTest {
	private SourceTableMapper sourceTableMapper;
	private SourceTableInfoService sourceTableInfoService;
	private SourceDataService sourceDataService;

	@BeforeEach
	void init() {
		sourceTableMapper = mock(SourceTableMapper.class);
		sourceTableInfoService = mock(SourceTableInfoService.class);
		sourceDataService = new SourceDataService(sourceTableMapper, sourceTableInfoService);
	}

	@Test
	@DisplayName("조회 쿼리가 정상적으로 작동하면, 조회 페이지가 반환됩니다.")
	void getPageValid() {
		String tableName = "customers";
		PageRequestDto pageRequestDto = new PageRequestDto(10, 20);
		List<Map<String, Object>> contents = new ArrayList<>();
		Long count = 50L;
		doReturn(contents).when(sourceTableMapper)
			.selectPage(tableName, pageRequestDto.getFirst(), pageRequestDto.getSize());
		doReturn(count).when(sourceTableMapper)
			.count(tableName);
		PageResponseDto<Map<String, Object>> pageResponseDto = sourceDataService.getPage(tableName, pageRequestDto);
		Assertions.assertEquals(contents, pageResponseDto.getContent());
		Assertions.assertEquals(3, pageResponseDto.getTotalPage());
	}

	@Test()
	@DisplayName("조회 쿼리에서 SQL 오류가 발생하면, 조회 기능에서 예외가 발생합니다.")
	void getPageInvalid() {
		String tableName = "customers";
		PageRequestDto pageRequestDto = new PageRequestDto(10, 20);
		doThrow(PersistenceException.class).when(sourceTableMapper)
			.selectPage(tableName, pageRequestDto.getFirst(), pageRequestDto.getSize());
		Assertions.assertThrows(PersistenceException.class, () -> sourceDataService.getPage(tableName, pageRequestDto));
	}

	@Test
	@DisplayName("삽입 쿼리가 정상적으로 작동하면, 데이터 삽입 기능 정상적으로 진행됩니다.")
	void insertValid() {
		String tableName = "customers";
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1L);
		data.put("name", "test1");
		sourceDataService.insert(tableName, data);
		verify(sourceTableInfoService, times(1)).checkTable(tableName);
		verify(sourceTableMapper, times(1)).insert(tableName, data);
	}

	@Test
	@DisplayName("삽입 쿼리에서 SQL 오류가 발생하면, 조회 쿼리에서 오류가 발생합니다.")
	void insertInvalid() {
		String tableName = "customers";
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1L);
		data.put("name", "test1");
		doThrow(PersistenceException.class).when(sourceTableMapper).insert(tableName, data);
		Assertions.assertThrows(PersistenceException.class, () -> sourceDataService.insert(tableName, data));
	}

	@Test
	@DisplayName("update 쿼리가 정상적으로 작동하면, 데이터 update 기능 정상적으로 진행됩니다.")
	void updateValid() {
		String tableName = "customers";
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1L);
		data.put("name", "test1");
		sourceDataService.update(tableName, data);
		verify(sourceTableInfoService, times(1)).checkTable(tableName);
		verify(sourceTableMapper, times(1)).updateByKey(tableName, "id", data);
	}

	@Test
	@DisplayName("update 쿼리에서 SQL 오류가 발생하면, 데이터 update 기능에서 오류가 발생합니다.")
	void updateInvalid() {
		String tableName = "customers";
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1L);
		data.put("name", "test1");
		doThrow(PersistenceException.class).when(sourceTableMapper).updateByKey(tableName, "id", data);
		Assertions.assertThrows(PersistenceException.class, () -> sourceDataService.update(tableName, data));
	}

	@Test
	@DisplayName("delete 쿼리가 정상적으로 작동하면, 데이터 delete 기능 정상적으로 진행됩니다.")
	void deleteValid() {
		String tableName = "customers";
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1L);
		data.put("name", "test1");
		sourceDataService.delete(tableName, data);
		verify(sourceTableInfoService, times(1)).checkTable(tableName);
		verify(sourceTableMapper, times(1)).deleteByKey(tableName, "id", data);
	}

	@Test
	@DisplayName("delete 쿼리에서 SQL 오류가 발생하면, 데이터 delete 기능에서 오류가 발생합니다.")
	void deleteInvalid() {
		String tableName = "customers";
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1L);
		data.put("name", "test1");
		doThrow(PersistenceException.class).when(sourceTableMapper).deleteByKey(tableName, "id", data);
		Assertions.assertThrows(PersistenceException.class, () -> sourceDataService.delete(tableName, data));
	}

}
