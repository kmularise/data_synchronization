package factory.integration.database.synchronizer.web.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;

import factory.integration.database.synchronizer.mapper.target.TargetColumnInfoMapper;
import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TargetTableInfoService {
	private final TargetColumnInfoMapper targetColumnInfoMapper;
	private final HikariConfig targetHikariConfig;

	public List<String> getAllTables() {
		return targetColumnInfoMapper.selectAllTableNames(targetHikariConfig.getSchema());
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
		return targetColumnInfoMapper.selectColumnsByTable(tableName, targetHikariConfig.getSchema());
	}
}
