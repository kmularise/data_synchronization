package factory.integration.database.synchronizer.mapper.source;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SourceColumnInfoMapper {
	List<String> selectColumnsByTable(@Param("tableName") String tableName, @Param("schemaName") String schemaName);

	List<String> selectAllTableNames(String schemaName);
}
