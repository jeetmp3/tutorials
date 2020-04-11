//package demo.multitenant.app.multipledb.config;
//
//import org.hibernate.MultiTenancyStrategy;
//import org.hibernate.cfg.Environment;
//import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
//import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.TransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableConfigurationProperties(JpaProperties.class)
//public class DataSourceConfig {
//
////    private final JpaProperties jpaProperties;
////
////    public DataSourceConfig(JpaProperties jpaProperties) {
////        this.jpaProperties = jpaProperties;
////    }
////
////    @Bean
////    JpaVendorAdapter jpaVendorAdapter() {
////        return new HibernateJpaVendorAdapter();
////    }
//
//    @Bean(name = "googleDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.google.datasource")
//    public DataSource googleDataSource() {
//        return DataSourceBuilder
//                .create(this.getClass().getClassLoader())
//                .build();
//    }
//
//    @Bean(name = "facebookDataSource")
//    @ConfigurationProperties(prefix = "spring.facebook.datasource")
//    public DataSource facebookDataSource() {
//        return DataSourceBuilder
//                .create(this.getClass().getClassLoader())
//                .build();
//    }
//
//    @Bean("googleEntityManagerFactory")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean googleEntityManagerFactory(
//            @Qualifier("googleDataSource") DataSource dataSource,
//            MultiTenantConnectionProvider multiTenantConnectionProvider,
//            CurrentTenantIdentifierResolver currentTenantIdentifierResolver
//    ) {
//
//        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
//        jpaPropertiesMap.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
//        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
//        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
//
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("demo.multitenant.app.multipledb*");
//        em.setJpaVendorAdapter(this.jpaVendorAdapter());
//        em.setJpaPropertyMap(jpaPropertiesMap);
//        return em;
//    }
//
//    @Bean("googleTransactionManager")
//    @Primary
//    public TransactionManager googleTransactionManager(DataSource googleDataSource) {
//        return new DataSourceTransactionManager(googleDataSource);
//    }
//
//    @Bean("facebookTransactionManager")
//    public TransactionManager facebookTransactionManager(@Qualifier("facebookDataSource") DataSource facebookDataSource) {
//        return new DataSourceTransactionManager(facebookDataSource);
//    }
//}
