package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "factory.integration.database.synchronizer.mapper.scheduler",
	sqlSessionFactoryRef = "schedulerSqlSessionFactory")
public class SchedulerMyBatisConfiguration {
	@Bean
	public SqlSessionFactory schedulerSqlSessionFactory(
		@Qualifier("schedulerDataSource") DataSource targetDataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(targetDataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
}
