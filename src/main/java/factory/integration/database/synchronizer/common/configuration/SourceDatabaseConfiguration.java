package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
public class SourceDatabaseConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.source" )
	public HikariConfig sourceHikariConfig() {
		return new HikariConfig();
	}

	@Bean
	@Primary
	public DataSource sourceDataSource() {
		return new HikariDataSource(sourceHikariConfig());
	}
}
