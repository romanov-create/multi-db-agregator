package com.example.multidbaggregator.config.model;

import org.springframework.jdbc.core.JdbcTemplate;

public record JdbcTemplateWithMapping(JdbcTemplate jdbcTemplate, MappingData mappingData) {
}
