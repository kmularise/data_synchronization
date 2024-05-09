package factory.integration.database.synchronizer.mapper.scheduler;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SyncTaskDaoMapper {
	List<SyncTask> selectAll();

	List<SyncTask> selectPage(@Param("first") Long first, @Param("size") int size);

	void truncate();

	void insert(SyncTask syncTask);

	Long count();
}
