package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QuartzDatabaseConfiguration {
	private final QuartzProperties quartzProperties;
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.quartz" )
	public HikariConfig quartzHikariConfig() {;
		return new HikariConfig();
	}

	@Bean
	public DataSource quartzDataSource() {
		return new HikariDataSource(quartzHikariConfig());
	}

	@Bean
	public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer() {
		return schedulerFactoryBean -> schedulerFactoryBean.setDataSource(quartzDataSource());
	}
}
