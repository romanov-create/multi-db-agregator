package com.example.multidbaggregator.service.impl;

import com.example.multidbaggregator.config.UserRowMapper;
import com.example.multidbaggregator.config.model.JdbcTemplateWithMapping;
import com.example.multidbaggregator.config.model.MappingData;
import com.example.multidbaggregator.model.UserEntity;
import com.example.multidbaggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private List<JdbcTemplateWithMapping> jdbcTemplateList;

    @Override
    public List<UserEntity> getAll(String name, String surname) {
        List<UserEntity> aggregatedUserEntities = new ArrayList<>();

        for (JdbcTemplateWithMapping jdbcTemplateWithMapping : jdbcTemplateList) {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplateWithMapping.jdbcTemplate());
            MappingData mappingData = jdbcTemplateWithMapping.mappingData();

            StringBuilder query = new StringBuilder("SELECT * FROM ").append(mappingData.getTable());
            MapSqlParameterSource parameters = new MapSqlParameterSource();

            appendConditionIfNotEmpty(query, parameters, "name", name, mappingData.getName());
            appendConditionIfNotEmpty(query, parameters, "surname", surname, mappingData.getSurname());

            List<UserEntity> users = namedParameterJdbcTemplate.query(query.toString(), parameters, new UserRowMapper(mappingData));
            aggregatedUserEntities.addAll(users);
        }

        return aggregatedUserEntities;
    }

    private void appendConditionIfNotEmpty(StringBuilder query, MapSqlParameterSource parameters, String paramName, String paramValue, String columnName) {
        if (paramValue != null && !paramValue.isEmpty()) {
            if (query.indexOf("WHERE") == -1) {
                query.append(" WHERE");
            } else {
                query.append(" AND");
            }

            query.append(" UPPER(").append(columnName).append(") LIKE UPPER(:").append(paramName).append(")");
            parameters.addValue(paramName, paramValue);
        }
    }
}
