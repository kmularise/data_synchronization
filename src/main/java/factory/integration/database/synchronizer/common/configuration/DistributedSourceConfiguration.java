package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@MapperScan(basePackages = "factory.integration.database.synchronizer.mapper.source",
	sqlSessionFactoryRef = "sourceDistributedSessionFactory")
public class DistributedSourceConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.source")
	public DataSource distributedSourceDataSource() {
		return new AtomikosDataSourceBean();
	}

	@Bean
	public SqlSessionFactory sourceDistributedSessionFactory(
		@Qualifier("distributedSourceDataSource") DataSource distributedSourceDataSource,
		ApplicationContext applicationContext) throws
		Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(distributedSourceDataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
}
