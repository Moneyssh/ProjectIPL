package com.manish.ipl.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
//@Import(PropertyPlaceHolderConfig.class)
//@PropertySource({"classpath:conf/taxcalculator-infrastructure.${APP_ENV}.properties"})
public class JpaConfig {

//    @Value("${jdbc.driver}")
//    private String driverClassName;

//    @Value("${jdbc.url}")
//    private String url;

//    @Value("${jdbc.user}")
//    private String user;

//    @Value("${jdbc.password}")
//    private String password;
    
    @Autowired
    private Environment env;

//    @Value("${hibernate.dialect:org.hibernate.dialect.HSQLDialect}")
//    private String hibernateDialect = "org.hibernate.dialect.HSQLDialect";

//    @Autowired
//    private JpaTransactionManager platformTransactionManager;

    @Bean
    public DataSource dataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName(driverClassName);
//        ds.setUrl(url);
//        ds.setUsername(user);
//        ds.setPassword(password);
//        ds.setDefaultAutoCommit(false);
//      return ds;
        String username = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");
		String driverClass = env.getProperty("spring.datasource.driver-class-name");
		String url = env.getProperty("spring.datasource.url");

		return DataSourceBuilder.create().username(username).password(password).url(url).driverClassName(driverClass)
				.build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setDatabasePlatform(hibernateDialect);
        return jpaVendorAdapter;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
//        entityManagerFactory.setPackagesToScan("be.cegeka.batchers.taxcalculator");

        Properties properties = new Properties();
        properties.put("hibernate.ddl-auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
//        if (getCurrentEnvironment().isMaster()) {
//            properties.put("hibernate.hbm2ddl.auto", "create-drop");
//        }

        entityManagerFactory.setJpaProperties(properties);
        entityManagerFactory.afterPropertiesSet();

        return entityManagerFactory.getNativeEntityManagerFactory();
    }

    /*
    @Bean
    public JpaTransactionManager transactionManager() {
        if (platformTransactionManager == null) {
            platformTransactionManager = new JpaTransactionManager(entityManagerFactory());
        }
        return platformTransactionManager;

    }*/
}