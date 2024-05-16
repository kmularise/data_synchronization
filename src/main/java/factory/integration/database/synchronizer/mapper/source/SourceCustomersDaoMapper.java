package factory.integration.database.synchronizer.mapper.source;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import factory.integration.database.synchronizer.mapper.Customer;

@Mapper
public interface SourceCustomersDaoMapper {
	Customer selectCustomerById(Long id);

	List<Customer> selectAll();

	void insertCustomer(Customer customer);

	void deleteCustomerById(Long id);

	void updateCustomerById(Customer customer);

	List<Customer> selectByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);
}
