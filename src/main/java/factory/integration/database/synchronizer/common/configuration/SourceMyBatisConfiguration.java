package factory.integration.database.synchronizer.common.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "factory.integration.database.synchronizer.mapper.source",
sqlSessionFactoryRef = "sourceSqlSessionFactory")
public class SourceMyBatisConfiguration {

	@Bean
	public SqlSessionFactory sourceSqlSessionFactory(
		@Qualifier("sourceDataSource")DataSource sourceDataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(sourceDataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	@Bean
	public DataSourceTransactionManager sourceTransactionManager(@Qualifier("sourceDataSource") DataSource sourceDataSource) {
		return new DataSourceTransactionManager(sourceDataSource);
	}
}
