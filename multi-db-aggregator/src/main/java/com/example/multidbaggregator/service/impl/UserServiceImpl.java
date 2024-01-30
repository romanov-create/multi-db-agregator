package com.example.multidbaggregator.service.impl;

import com.example.multidbaggregator.config.DatabaseConfig;
import com.example.multidbaggregator.config.MappingData;
import com.example.multidbaggregator.config.DatabaseProperties;
import com.example.multidbaggregator.model.UserEntity;
import com.example.multidbaggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Override
    public List<UserEntity> getAll(String name, String surname) {
        List<UserEntity> aggregatedUserEntities = new ArrayList<>();

        for (DatabaseConfig dbConfig : databaseProperties.getDatasources()) {
            try (Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword())) {
                MappingData mapping = dbConfig.getMapping();
                Statement statement = connection.createStatement();

                String selectQuery = buildSelectQuery(dbConfig, name, surname);
                ResultSet resultSet = statement.executeQuery(selectQuery);

                while (resultSet.next()) {
                    UserEntity user = new UserEntity(
                            resultSet.getLong(mapping.getId()),
                            resultSet.getString(mapping.getUsername()),
                            resultSet.getString(mapping.getName()),
                            resultSet.getString(mapping.getSurname())
                    );

                    aggregatedUserEntities.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return aggregatedUserEntities;
    }

    private String buildSelectQuery(DatabaseConfig dbConfig, String name, String surname) {
        MappingData mapping = dbConfig.getMapping();
        StringBuilder selectQuery = new StringBuilder("SELECT * FROM ").append(dbConfig.getTable());

        addFilterCondition(selectQuery, " UPPER(" + dbConfig.getTable() + "." + mapping.getName() + ") LIKE UPPER('" + name + "')", name);
        addFilterCondition(selectQuery, " UPPER(" + dbConfig.getTable() + "." + mapping.getSurname() + ") LIKE UPPER('" + surname + "')", surname);

        return selectQuery.toString();
    }

    private void addFilterCondition(StringBuilder query, String condition, String param) {
        if (param != null) {
            if (query.indexOf("WHERE") == -1) {
                query.append(" WHERE");
            } else {
                query.append(" AND");
            }
            query.append(condition);
        }
    }
}
