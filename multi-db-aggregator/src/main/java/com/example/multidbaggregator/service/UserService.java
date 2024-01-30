package com.example.multidbaggregator.service;

import com.example.multidbaggregator.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAll(String name, String surname);
}
