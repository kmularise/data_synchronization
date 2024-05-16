package factory.integration.database.synchronizer.mapper.target;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import factory.integration.database.synchronizer.mapper.Customer;

@Mapper
public interface TargetCustomersDaoMapper {
	Customer selectCustomerById(Long id);

	List<Customer> selectAll();

	void insertCustomer(Customer customer);

	void deleteCustomerById(Long id);

	void updateCustomerById(Customer customer);
}
