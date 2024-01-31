package com.example.multidbaggregator.config.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConfig {

    private String name;
    private String url;
    private String table;
    private String username;
    private String password;
    private String driverClassName;
    private MappingData mapping;

}
