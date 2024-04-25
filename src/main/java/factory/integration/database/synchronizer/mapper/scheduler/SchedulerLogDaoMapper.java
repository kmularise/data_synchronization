package factory.integration.database.synchronizer.mapper.scheduler;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulerLogDaoMapper {
	void insertLog(TaskDto taskDto);
}
