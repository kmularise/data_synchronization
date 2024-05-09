package factory.integration.database.synchronizer.scheduler.initialization;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import factory.integration.database.synchronizer.service.SchedulerExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerInfoLoader implements ApplicationRunner {
	private final SchedulerExecutionService schedulerExecutionService;

	@Override
	public void run(ApplicationArguments args) {
		log.info("Scheduler Info Loader start!");
		schedulerExecutionService.enrollAllTasks();
	}
}
