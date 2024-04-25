package factory.integration.database.synchronizer.scheduler.job;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import factory.integration.database.synchronizer.mapper.CustomerDto;
import factory.integration.database.synchronizer.mapper.source.SourceCustomersDaoMapper;
import factory.integration.database.synchronizer.mapper.target.TargetCustomersDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SynchronizationJob implements Job {
	private final SourceCustomersDaoMapper sourceCustomersDaoMapper;
	private final TargetCustomersDaoMapper targetCustomersDaoMapper;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("data synchronization starts!");
		List<CustomerDto> sourceCustomerDtos = sourceCustomersDaoMapper.selectAll();
		List<CustomerDto> targetCustomerDtos = targetCustomersDaoMapper.selectAll();
		Set<CustomerDto> sourceSet = new HashSet<>(sourceCustomerDtos);
		Set<CustomerDto> targetSet = new HashSet<>(targetCustomerDtos);
		// insertMissingEntries(sourceSet, targetSet);
		// deleteEntriesNotInSource(sourceSet, targetSet);
		// updateChangedEntries(sourceSet, targetSet);
	}

	private void insertMissingEntries(Set<CustomerDto> sourceSet, Set<CustomerDto> targetSet) {
		sourceSet.stream()
			.filter(s -> !targetSet.contains(s))
			.forEach(targetCustomersDaoMapper::insertCustomer);
	}

	private void deleteEntriesNotInSource(Set<CustomerDto> sourceSet, Set<CustomerDto> targetSet) {
		targetSet.stream()
			.filter(t -> !sourceSet.contains(t))
			.forEach(t -> targetCustomersDaoMapper.deleteCustomerById(t.getId()));

	}

	private void updateChangedEntries(Set<CustomerDto> sourceSet, Set<CustomerDto> targetSet) {
		sourceSet.stream()
			.filter(targetSet::contains)
			.forEach(s -> {
				if (!s.equals(targetCustomersDaoMapper.selectCustomerById(s.getId()))) {
					targetCustomersDaoMapper.updateCustomerById(s);
				}
			});
	}
}
