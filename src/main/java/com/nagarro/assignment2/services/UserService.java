package com.nagarro.assignment2.services;

import java.util.List;

import com.nagarro.assignment2.model.UserEntity;

public interface UserService {
    public List<UserEntity> getUsers();
    List<UserEntity> getUsers(String sortType, String sortOrder, int limit, int offset);
}
