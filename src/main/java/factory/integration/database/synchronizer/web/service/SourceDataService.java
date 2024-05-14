package factory.integration.database.synchronizer.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.source.SourceTableMapper;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SourceDataService {
	private final SourceTableMapper sourceTableMapper;
	private final SourceTableInfoService sourceTableInfoService;

	//TODO: page, size 묶어서 하나로 처리하기 Pageable
	@Transactional(transactionManager = "sourceTransactionManager")
	public PageResponseDto<Map<String, Object>> getPage(String tableName, PageRequestDto pageDto) {
		sourceTableInfoService.checkTable(tableName);
		List<Map<String, Object>> contents = sourceTableMapper.selectPage(tableName, pageDto.getFirst(),
			pageDto.getSize());
		return new PageResponseDto<>(pageDto.getSize(), sourceTableMapper.count(tableName), contents);
	}

	@Transactional(transactionManager = "sourceTransactionManager")
	public void insert(String tableName, Map<String, Object> data) {
		sourceTableInfoService.checkTable(tableName);
		sourceTableMapper.insert(tableName, data);
	}

	@Transactional(transactionManager = "sourceTransactionManager")
	public void update(String tableName, Map<String, Object> data) {
		sourceTableInfoService.checkTable(tableName);
		sourceTableMapper.updateByKey(tableName, "id", data);
	}

	@Transactional(transactionManager = "sourceTransactionManager")
	public void delete(String tableName, Map<String, Object> data) {
		sourceTableInfoService.checkTable(tableName);
		sourceTableMapper.deleteByKey(tableName, "id", data);
	}

	@Transactional(transactionManager = "sourceTransactionManager", readOnly = true)
	public List<String> getTableColumns(String tableName) {
		return sourceTableInfoService.getColumns(tableName);
	}

	@Transactional(transactionManager = "sourceTransactionManager", readOnly = true)
	public List<String> getAllTables() {
		return sourceTableInfoService.getAllTables();
	}
}
