package ua.procamp.footballmanager.config;

import org.h2.tools.Server;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan(
        basePackages = {"ua.procamp.footballmanager"},
        excludeFilters = {@ComponentScan.Filter({Controller.class, EnableWebMvc.class})}
)
@EnableJpaRepositories("ua.procamp.footballmanager.repository")
@EnableTransactionManagement
public class AppConfig {

    @Value("${db.config.url:jdbc:postgresql://localhost:5432/football-manager}")
    private String dbUrl;

    @Value("${db.config.username:manager}")
    private String dbUsername;

    @Value("${db.config.password:manager}")
    private String dbPassword;

    @Bean
    public PostgreSQLContainer postgreSQLContainer() {

        String[] dbUrlParts = dbUrl.split("/");

        String dbName = dbUrlParts[dbUrlParts.length - 1];
        String hostWithPort = dbUrlParts[dbUrlParts.length - 2];
        Integer port = getPortFromAddress(hostWithPort);

        PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>()
                .withDatabaseName(dbName)
                .withPassword(dbUsername)
                .withUsername(dbPassword)
                .withExposedPorts(port);
        postgreSQLContainer.addParameter(
                "v", "/var/lib/postgresql/data:/var/lib/postgresql/data");
        postgreSQLContainer.start();

        Integer mappedPort = postgreSQLContainer.getMappedPort(port);
        dbUrl = replacePort(dbUrl, port, mappedPort);

        return postgreSQLContainer;
    }

    @Bean
    @DependsOn("postgreSQLContainer")
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUser(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    private Integer getPortFromAddress(String address) {
        String[] addressParts = address.split(":");
        return Integer.valueOf(addressParts[addressParts.length - 1]);
    }

    private String replacePort(String address, Integer oldPort, Integer newPort) {
        return address.replace(oldPort.toString(), newPort.toString());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setJpaProperties(hibernateProperties());
        entityManagerFactory.setPackagesToScan("ua.procamp.footballmanager.model.entity");
        return entityManagerFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("javax.persistence.schema-generation.database.action", "create");
        properties.put("javax.persistence.schema-generation.create-source", "script");
        properties.put("javax.persistence.schema-generation.create-script-source", "schema.sql");
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "validate");

        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
