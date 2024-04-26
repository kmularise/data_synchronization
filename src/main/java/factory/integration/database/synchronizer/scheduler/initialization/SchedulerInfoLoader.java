package factory.integration.database.synchronizer.scheduler.initialization;

import org.springframework.stereotype.Component;

import factory.integration.database.synchronizer.service.SchedulerExecutionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerInfoLoader {
	private final SchedulerExecutionService schedulerExecutionService;

	@PostConstruct
	public void init() {
		log.info("Scheduler Info Loader start!");
		schedulerExecutionService.initAllSchedulers();
	}
}
