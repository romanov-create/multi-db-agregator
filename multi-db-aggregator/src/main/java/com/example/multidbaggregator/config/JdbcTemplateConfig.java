package com.example.multidbaggregator.config;

import com.example.multidbaggregator.config.model.DatabaseConfig;
import com.example.multidbaggregator.config.model.JdbcTemplateWithMapping;
import com.example.multidbaggregator.config.model.MappingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class JdbcTemplateConfig {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean
    public List<JdbcTemplateWithMapping> jdbcTemplateList() {
        return databaseProperties.getDatasources().stream()
                .map(this::createJdbcTemplateWithMapping)
                .toList();
    }

    private JdbcTemplateWithMapping createJdbcTemplateWithMapping(DatabaseConfig databaseConfig) {
        DataSource dataSource = createDataSource(databaseConfig);
        MappingData mappingData = databaseConfig.getMapping();

        return new JdbcTemplateWithMapping(new JdbcTemplate(dataSource), mappingData);
    }

    private DataSource createDataSource(DatabaseConfig databaseConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(databaseConfig.getDriverClassName());
        dataSource.setUrl(databaseConfig.getUrl());
        dataSource.setUsername(databaseConfig.getUsername());
        dataSource.setPassword(databaseConfig.getPassword());

        return dataSource;
    }
}
