package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SchedulerDatabaseConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.quartz")
	public HikariConfig schedulerHikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource schedulerDataSource() {
		return new HikariDataSource(schedulerHikariConfig());
	}
}
