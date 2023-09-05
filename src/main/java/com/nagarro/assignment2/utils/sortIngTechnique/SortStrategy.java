package com.nagarro.assignment2.utils.sortIngTechnique;

import java.util.List;

import com.nagarro.assignment2.model.UserEntity;

public interface SortStrategy {
    List<UserEntity> sort(List<UserEntity> users);
}

