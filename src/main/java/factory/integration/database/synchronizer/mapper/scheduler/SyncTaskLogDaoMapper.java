package factory.integration.database.synchronizer.mapper.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SyncTaskLogDaoMapper {
	void insertLog(SyncTaskLog syncTaskLog);

	void updateLog(@Param("id") Long id, @Param("status") TaskStatus status, @Param("endTime") LocalDateTime endTime);

	List<SyncTaskLog> selectPage(@Param("first") Long first, @Param("size") int size);
}
