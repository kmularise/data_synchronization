package factory.integration.database.synchronizer.mapper.target;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import factory.integration.database.synchronizer.mapper.CarDto;
import factory.integration.database.synchronizer.mapper.CustomerDto;
import factory.integration.database.synchronizer.mapper.OrderDto;

@Mapper
public interface TargetDaoMapper {
	void insertTemporaryCustomers(@Param("customers") List<CustomerDto> customers);

	void insertTemporaryCars(@Param("cars") List<CarDto> cars);

	void insertTemporaryOrders(@Param("orders") List<OrderDto> orders);

	void deleteCarsNotInTemporaryCars();

	void insertCarsFromTemporaryCars();

	void deleteOrdersNotInTemporaryOrders();

	void insertOrdersFromTemporaryOrders();

	void deleteCustomersNotInTemporaryCustomers();

	void insertCustomersNotInTemporaryCustomers();

	void updateCarsFromTemporaryCars();

	void updateCustomersFromTemporaryCustomers();

	void updateOrdersFromTemporaryOrders();

	void deleteAllTemporaryCars();

	void deleteAllTemporaryOrders();

	void deleteAllTemporaryCustomers();
}
