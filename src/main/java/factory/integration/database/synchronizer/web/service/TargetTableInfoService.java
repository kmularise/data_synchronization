package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;

import factory.integration.database.synchronizer.mapper.source.ColumnInfoMapper;
import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TargetTableInfoService {
	private final ColumnInfoMapper columnInfoMapper;
	private final HikariConfig targetHikariConfig;

	public List<String> getAllTables() {
		return columnInfoMapper.selectAllTableNames(targetHikariConfig.getSchema());
	}

	public void checkTable(String tableName) {
		List<String> tables = getAllTables();
		for (String existedTable : tables) {
			if (existedTable.equals(tableName)) {
				return;
			}
		}
		throw new BusinessException(ErrorMessage.INVALID_TABLE, HttpStatus.BAD_REQUEST.value());
	}

	public List<String> getColumns(String tableName) {
		checkTable(tableName);
		return columnInfoMapper.selectColumnsByTable(tableName, targetHikariConfig.getSchema());
	}
}
