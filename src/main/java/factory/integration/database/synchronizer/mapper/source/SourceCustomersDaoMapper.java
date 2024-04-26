package factory.integration.database.synchronizer.mapper.source;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import factory.integration.database.synchronizer.mapper.CustomerDto;

@Mapper
public interface SourceCustomersDaoMapper {
	CustomerDto selectCustomerById(Long id);
	List<CustomerDto> selectAll();
	void insertCustomer(CustomerDto customerDto);
	void deleteCustomerById(Long id);
	void updateCustomerById(CustomerDto customerDto);
	List<CustomerDto> selectByIdRange(@Param("startId")Long startId, @Param("endId") Long endId);
}
