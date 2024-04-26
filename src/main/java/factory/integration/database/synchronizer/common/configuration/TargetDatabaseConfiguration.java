package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
public class TargetDatabaseConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.target" )
	public HikariConfig targetHikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource targetDataSource() {
		return new HikariDataSource(targetHikariConfig());
	}

	@Bean
	public DataSourceTransactionManager targetTransactionManager(
		@Qualifier("targetDataSource") DataSource targetDataSource) {
		return new DataSourceTransactionManager(targetDataSource);
	}
}
