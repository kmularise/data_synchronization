package factory.integration.database.synchronizer.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;

import factory.integration.database.synchronizer.mapper.source.ColumnInfoMapper;
import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SourceTableInfoService {
	private final ColumnInfoMapper columnInfoMapper;
	private final HikariConfig sourceHikariConfig;

	public void checkColumns(String tableName, List<String> columns) {
		Set<String> validColumns = new HashSet<>(getColumns(tableName));
		for (String excludedColumn : columns) {
			if (!validColumns.contains(excludedColumn)) {
				throw new BusinessException(ErrorMessage.INVALID_COLUMN, HttpStatus.BAD_REQUEST.value());
			}
		}
	}

	public List<String> getIncludedColumns(String tableName, List<String> excludedColumns) {
		Set<String> validColumns = new HashSet<>(getColumns(tableName));
		for (String excludedColumn : excludedColumns) {
			if (!validColumns.contains(excludedColumn)) {
				throw new BusinessException(ErrorMessage.INVALID_COLUMN, HttpStatus.BAD_REQUEST.value());
			}
			validColumns.remove(excludedColumn);
		}
		return validColumns.stream().toList();
	}

	public List<String> getColumns(String tableName) {
		checkTable(tableName);
		return columnInfoMapper.selectColumnsByTable(tableName, sourceHikariConfig.getSchema());
	}

	public void checkTable(String tableName) {
		List<String> tableNames = getAllTables();
		for (String existedTable : tableNames) {
			if (existedTable.equals(tableName)) {
				return;
			}
		}
		throw new BusinessException(ErrorMessage.INVALID_TABLE, HttpStatus.BAD_REQUEST.value());
	}

	public List<String> getAllTables() {
		return columnInfoMapper.selectAllTableNames(sourceHikariConfig.getSchema());
	}

}
