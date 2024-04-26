package factory.integration.database.synchronizer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import factory.integration.database.synchronizer.mapper.CarDto;
import factory.integration.database.synchronizer.mapper.CustomerDto;
import factory.integration.database.synchronizer.mapper.OrderDto;
import factory.integration.database.synchronizer.mapper.source.SourceDaoMapper;
import factory.integration.database.synchronizer.mapper.target.TargetDaoMapper;
import factory.integration.database.synchronizer.scheduler.job.SyncTableInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SyncService {
	private final SourceDaoMapper sourceDaoMapper;
	private final TargetDaoMapper targetDaoMapper;

	@Transactional
	public void synchronize(SyncTableInfo syncTableInfo) {
		log.info("syncTableInfo: {}", syncTableInfo);
		if (syncTableInfo.getTableName().equals("customers")) {
			synchronizeCustomers(syncTableInfo);
			return;
		} else if (syncTableInfo.getTableName().equals("orders")) {
			synchronizeOrders(syncTableInfo);
			return;
		} else if (syncTableInfo.getTableName().equals("cars")) {
			synchronizeCars(syncTableInfo);
			return;
		}
		throw new IllegalArgumentException("invalid table name");
	}

	private void synchronizeCustomers(SyncTableInfo syncTableInfo) {
		List<CustomerDto> customerDtos = sourceDaoMapper.selectAllFromCustomers();
		if (customerDtos != null && !customerDtos.isEmpty()) {
			targetDaoMapper.insertTemporaryCustomers(customerDtos);
		}
		if (syncTableInfo.getHasInserted()) {
			targetDaoMapper.insertCustomersNotInTemporaryCustomers();
		}
		if (syncTableInfo.getHasDeleted()) {
			targetDaoMapper.deleteCustomersNotInTemporaryCustomers();
		}
		if (syncTableInfo.getHasUpdated()) {
			targetDaoMapper.updateCustomersFromTemporaryCustomers();
		}
		targetDaoMapper.deleteAllTemporaryCustomers();
	}

	private void synchronizeOrders(SyncTableInfo syncTableInfo) {
		List<OrderDto> orderDtos = sourceDaoMapper.selectAllFromOrders();
		if (orderDtos != null && !orderDtos.isEmpty()) {
			targetDaoMapper.insertTemporaryOrders(orderDtos);
		}
		if (syncTableInfo.getHasInserted()) {
			targetDaoMapper.insertOrdersFromTemporaryOrders();
		}
		if (syncTableInfo.getHasDeleted()) {
			targetDaoMapper.deleteOrdersNotInTemporaryOrders();
		}
		if (syncTableInfo.getHasUpdated()) {
			targetDaoMapper.updateOrdersFromTemporaryOrders();
		}
		targetDaoMapper.deleteAllTemporaryOrders();
	}

	private void synchronizeCars(SyncTableInfo syncTableInfo) {
		List<CarDto> carDtos = sourceDaoMapper.selectAllFromCars();
		if (carDtos != null && !carDtos.isEmpty()) {
			targetDaoMapper.insertTemporaryCars(carDtos);
		}
		if (syncTableInfo.getHasInserted()) {
			targetDaoMapper.insertCarsFromTemporaryCars();
		}
		if (syncTableInfo.getHasDeleted()) {
			targetDaoMapper.deleteCarsNotInTemporaryCars();
		}
		if (syncTableInfo.getHasUpdated()) {
			targetDaoMapper.updateCarsFromTemporaryCars();
		}
		targetDaoMapper.deleteAllTemporaryCars();
	}
}
