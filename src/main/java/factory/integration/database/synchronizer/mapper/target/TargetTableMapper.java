package factory.integration.database.synchronizer.mapper.target;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TargetTableMapper {
	int insertAll(@Param("tableName") String tableName, @Param("list") List<Map<String, Object>> list);

	int deleteFromTableNotInTemporaryTable(
		@Param("tableName") String tableName,
		@Param("temporaryTable") String temporaryTable,
		@Param("key") String key);

	int insertTableFromTemporaryTable(
		@Param("tableName") String tableName,
		@Param("temporaryTable") String temporaryTable,
		@Param("columns") List<String> columns,
		@Param("key") String key);

	int updateTableFromTemporaryTable(
		@Param("tableName") String tableName,
		@Param("temporaryTable") String temporaryTable,
		@Param("columns") List<String> columns,
		@Param("key") String key);

	List<Map<String, Object>> selectAllFromTable(@Param("tableName") String tableName);

	void deleteAll(@Param("tableName") String tableName);

	void truncateTable(String tableName);
}
