package com.example.multidbaggregator.config;

import com.example.multidbaggregator.config.model.MappingData;
import com.example.multidbaggregator.model.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserEntity> {

    private final MappingData mappingData;

    public UserRowMapper(MappingData mappingData) {
        this.mappingData = mappingData;
    }

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserEntity user = new UserEntity();

        user.setId(rs.getLong(mappingData.getId()));
        user.setUsername(rs.getString(mappingData.getUsername()));
        user.setName(rs.getString(mappingData.getName()));
        user.setSurname(rs.getString(mappingData.getSurname()));

        return user;
    }
}