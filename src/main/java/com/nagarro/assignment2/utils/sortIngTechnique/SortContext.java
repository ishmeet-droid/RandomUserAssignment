package com.nagarro.assignment2.utils.sortIngTechnique;

import java.util.List;

import com.nagarro.assignment2.model.UserEntity;

public class SortContext {
    private final SortStrategy strategy;

    public SortContext(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public List<UserEntity> executeSort(List<UserEntity> users) {
        return strategy.sort(users);
    }
}

