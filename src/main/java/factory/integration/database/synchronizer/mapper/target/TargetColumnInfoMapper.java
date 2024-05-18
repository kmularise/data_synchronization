package factory.integration.database.synchronizer.mapper.target;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TargetColumnInfoMapper {
	List<String> selectColumnsByTable(@Param("tableName") String tableName, @Param("schemaName") String schemaName);

	List<String> selectAllTableNames(String schemaName);
}
