package factory.integration.database.synchronizer.mapper.source;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SourceTableMapper {
	List<Map<String, Object>> selectAllFromTable(@Param("tableName") String tableName);

	List<Map<String, Object>> selectNextPageByCursor(@Param("tableName") String tableName,
		@Param("cursor") Long cursor,
		@Param("size") int size);

	List<Map<String, Object>> selectPrevPageByCursor(@Param("tableName") String tableName,
		@Param("cursor") Long cursor,
		@Param("size") int size);

	int insert(@Param("tableName") String tableName, @Param("mapData") Map<String, Object> mapData);

	void updateByKey(@Param("tableName") String tableName,
		@Param("targetKey") String targetKey,
		@Param("mapData") Map<String, Object> mapData);

	int deleteByKey(@Param("tableName") String tableName,
		@Param("targetKey") String targetKey,
		@Param("mapData") Map<String, Object> mapData);

	void insertAll(@Param("tableName") String tableName, @Param("list") List<Map<String, Object>> list);

	void truncateTable(String tableName);
}
