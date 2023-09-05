package com.nagarro.assignment2.utils.sortIngTechnique;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.nagarro.assignment2.model.UserEntity;

public class AgeSortStrategy implements SortStrategy {
    private final boolean even;

    public AgeSortStrategy(boolean even) {
        this.even = even;
    }

    @Override
    public List<UserEntity> sort(List<UserEntity> users) {
        return users.stream()
                .filter(user -> (user.getAge() % 2 == 0) == even)
                .sorted(Comparator.comparing(UserEntity::getAge))
                .collect(Collectors.toList());
    }
}
