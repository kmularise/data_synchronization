package factory.integration.database.synchronizer.test.integrationtest.parallel;

import static java.util.concurrent.TimeUnit.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import factory.integration.database.synchronizer.scheduler.job.SyncTaskInfoRequest;
import factory.integration.database.synchronizer.service.SyncService;
import factory.integration.database.synchronizer.test.integrationtest.IntegrationTestBase;

public class ParallelTest extends IntegrationTestBase {
	@Autowired
	SyncService syncService;
	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	@DisplayName("데이터 동기화 작업이 멀티 스레드 환경에서 정상적으로 실행되는지 검증")
	@Test
	void executeParallel() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(3);
		List<String> tables = List.of("customers", "orders", "cars");
		AtomicLong customerThreadId = new AtomicLong(-1);
		AtomicLong orderThreadId = new AtomicLong(-1);
		AtomicLong carThreadId = new AtomicLong(-1);
		AtomicLong successCount = new AtomicLong();
		AtomicLong failureCount = new AtomicLong();
		List<AtomicLong> threadIds = List.of(customerThreadId, orderThreadId, carThreadId);
		for (int index = 0; index < 3; index++) {
			final int i = index;
			executorService.submit(() -> {
				try {
					syncService.synchronize(
						new SyncTaskInfoRequest(10L, tables.get(i), true, true, true, new ArrayList<>()));
					threadIds.get(i).set(Thread.currentThread().getId());
					successCount.getAndIncrement();
				} catch (Exception exception) {
					failureCount.incrementAndGet();
				} finally {
					threadIds.get(i).set(Thread.currentThread().getId());
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await(2000, SECONDS);
		assertEquals(3L, successCount.get());
		assertEquals(0L, failureCount.get());
		for (int i = 0; i < 3; i++) {
			for (int j = i + 1; j < 3; j++) {
				assertNotEquals(threadIds.get(i), threadIds.get(j));
			}
		}
	}
}
