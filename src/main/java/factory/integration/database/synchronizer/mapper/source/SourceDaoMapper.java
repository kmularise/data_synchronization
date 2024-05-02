package factory.integration.database.synchronizer.mapper.source;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import factory.integration.database.synchronizer.mapper.CarDto;
import factory.integration.database.synchronizer.mapper.CustomerDto;
import factory.integration.database.synchronizer.mapper.OrderDto;

@Mapper
public interface SourceDaoMapper {
	List<Map<String, Object>> selectAllFromTable(@Param("tableName") String tableName);

	List<CustomerDto> selectAllFromCustomers();

	List<CarDto> selectAllFromCars();

	List<OrderDto> selectAllFromOrders();

	List<Map<String, Object>> selectByIdRangeFromTable(
		@Param("tableName") String tableName,
		@Param("startId") Long startId,
		@Param("endId") Long endId);

}
