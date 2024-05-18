package factory.integration.database.synchronizer.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.target.TargetTableMapper;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TargetDataService {
	private final TargetTableMapper targetTableMapper;
	private final TargetTableInfoService targetTableInfoService;

	@Transactional(transactionManager = "targetTransactionManager", readOnly = true)
	public List<String> getTableColumns(String tableName) {
		return targetTableInfoService.getColumns(tableName);
	}

	@Transactional(transactionManager = "targetTransactionManager", readOnly = true)
	public List<String> getAllTables() {
		return targetTableInfoService.getAllTables();
	}

	public PageResponseDto<Map<String, Object>> getPage(String tableName, PageRequestDto pageRequestDto) {
		List<Map<String, Object>> content = targetTableMapper.selectPage(tableName, pageRequestDto.getFirst(),
			pageRequestDto.getSize());
		return new PageResponseDto<>(pageRequestDto.getSize(), targetTableMapper.count(tableName), content);
	}
}
