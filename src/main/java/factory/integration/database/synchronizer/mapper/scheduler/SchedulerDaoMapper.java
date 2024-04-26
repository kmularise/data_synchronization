package factory.integration.database.synchronizer.mapper.scheduler;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulerDaoMapper {
	List<SchedulerDto> selectAll();
}
