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
@MapperScan(basePackages = "factory.integration.database.synchronizer.mapper.target",
	sqlSessionFactoryRef = "targetDistributedSessionFactory")
public class DistributedTargetConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.target")
	public DataSource distributedTargetDataSource() {
		return new AtomikosDataSourceBean();
	}

	@Bean
	public SqlSessionFactory targetDistributedSessionFactory(@Qualifier("distributedTargetDataSource")DataSource distributedTargetDataSource, ApplicationContext applicationContext) throws
		Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(distributedTargetDataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
}
