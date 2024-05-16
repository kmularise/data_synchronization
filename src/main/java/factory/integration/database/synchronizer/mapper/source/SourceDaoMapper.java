package factory.integration.database.synchronizer.mapper.source;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import factory.integration.database.synchronizer.mapper.Car;
import factory.integration.database.synchronizer.mapper.Customer;
import factory.integration.database.synchronizer.mapper.Order;

@Mapper
public interface SourceDaoMapper {
	List<Map<String, Object>> selectAllFromTable(@Param("tableName") String tableName);

	List<Customer> selectAllFromCustomers();

	List<Car> selectAllFromCars();

	List<Order> selectAllFromOrders();

	List<Map<String, Object>> selectByIdRangeFromTable(
		@Param("tableName") String tableName,
		@Param("startId") Long startId,
		@Param("endId") Long endId);

}
