package factory.integration.database.synchronizer.mapper.target;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import factory.integration.database.synchronizer.mapper.Car;
import factory.integration.database.synchronizer.mapper.Customer;
import factory.integration.database.synchronizer.mapper.Order;

@Mapper
public interface TargetDaoMapper {
	void insertTemporaryCustomers(@Param("customers") List<Customer> customers);

	void insertTemporaryCars(@Param("cars") List<Car> cars);

	void insertTemporaryOrders(@Param("orders") List<Order> orders);

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

	void deleteAllTemporaryCustomers();
}
