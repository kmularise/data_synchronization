package factory.integration.database.mapper.target;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import factory.integration.database.mapper.CustomerDto;

@Mapper
public interface TargetCustomersDaoMapper {
	CustomerDto selectCustomerById(Long id);
	List<CustomerDto> selectAll();

	void insertCustomer(CustomerDto customerDto);
	void deleteCustomerById(Long id);
	void updateCustomerById(CustomerDto customerDto);
}
