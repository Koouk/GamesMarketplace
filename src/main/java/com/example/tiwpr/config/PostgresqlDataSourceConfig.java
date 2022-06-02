package com.example.tiwpr.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(value = "datasource.type", havingValue = "POSTGRESQL")
@Slf4j
public class PostgresqlDataSourceConfig {

    @Value("${datasource.host}")
    String host;

    @Value("${datasource.port}")
    Integer port;

    @Value("${datasource.database}")
    String database;

    @Value("${datasource.username}")
    String username;

    @Value("${datasource.password}")
    String password;

    @Bean
    @Primary
    public DataSource postgresqlDataSource() {
        log.info("Starting with postgresql database...");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://" + host + ":" + port + "/" + database);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);

        return dataSourceBuilder.build();
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-postgres.yaml");
        liquibase.setDataSource(postgresqlDataSource());
        return liquibase;
    }
}
