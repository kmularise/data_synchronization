package factory.integration.database.synchronizer.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import jakarta.transaction.UserTransaction;
@Configuration
public class DistributedTransactionConfiguration {

	@Primary
	@Bean
	public PlatformTransactionManager distributedTransactionManager() throws Exception {
		UserTransaction userTransaction = new UserTransactionImp();
		userTransaction.setTransactionTimeout(60);
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		JtaTransactionManager manager = new JtaTransactionManager(
			userTransaction, userTransactionManager);
		return manager;
	}
}
