package dev.paie.spring;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(DataSourceConfig.class)
public class JpaConfig {

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory( entityManagerFactory(dataSource));
		return txManager;
	}

	// Cette configuration nécessite une source de données configurée.
	// Elle s'utilise donc en association avec un autre fichier de configuration
	// définissant un bean DataSource.
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		//vendorAdapter.
		// activer les logs SQL
		//vendorAdapter.setShowSql(true);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		// alternative au persistence.xml
		factory.setPackagesToScan("dev.paie.entite");
		factory.setDataSource(dataSource);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect");
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	
}
