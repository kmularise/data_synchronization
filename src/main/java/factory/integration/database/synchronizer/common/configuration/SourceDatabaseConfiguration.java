package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SourceDatabaseConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.source")
	public HikariConfig sourceHikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource sourceDataSource() {
		return new HikariDataSource(sourceHikariConfig());
	}
}
