package com.example.multidbaggregator.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "databases")
public class DatabaseProperties {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseProperties.class);

    private static final String CONFIG_FILE = "databases-props.yml";


    private final  List<DatabaseConfig> datasources = new ArrayList<>();

    public DatabaseProperties() {
        loadDatabaseConfigs(datasources);
        logDatabaseProperties();
    }


    public static void loadDatabaseConfigs(List<DatabaseConfig> datasources) {
        try (InputStream input = DatabaseProperties.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            Yaml yaml = new Yaml();
            Map<String, Object> obj = yaml.load(input);

            if (obj.containsKey("databases") && obj.get("databases") instanceof List) {
                List<Map<String, Object>> databasesList = (List<Map<String, Object>>) obj.get("databases");

                for (Map<String, Object> database : databasesList) {
                    DatabaseConfig config = new DatabaseConfig();
                    config.setName((String) database.get("name"));
                    config.setUrl((String) database.get("url"));
                    config.setTable((String) database.get("table"));
                    config.setUsername((String) database.get("username"));
                    config.setPassword((String) database.get("password"));
                    config.setDriverClassName((String) database.get("driverClassName"));

                    if(database.containsKey("mapping") && database.containsValue(database.get("mapping"))) {
                        MappingData mappingData = new MappingData();
                        Map<String, Object> mapping = (Map<String, Object>) database.get("mapping");
                        mappingData.setId((String) mapping.get("id"));
                        mappingData.setUsername((String) mapping.get("username"));
                        mappingData.setName((String) mapping.get("name"));
                        mappingData.setSurname((String) mapping.get("surname"));

                        config.setMapping(mappingData);
                    }

                    datasources.add(config);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Обработка ошибок загрузки конфигурации
        }
    }

    private void logDatabaseProperties() {
        if (!datasources.isEmpty()) {
            for (int i = 0; i < datasources.size(); i++) {
                DatabaseConfig config = datasources.get(i);
                logger.info("Database Name {}: {}", i + 1, config.getName());
                logger.info("Database URL {}: {}", i + 1, config.getUrl());
                // добавьте логгирование других свойств по необходимости
            }
        } else {
            logger.warn("No databases configured.");
        }
    }
}
