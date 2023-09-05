package com.nagarro.assignment2.utils.sortIngTechnique;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.nagarro.assignment2.model.UserEntity;

public class NameLengthSortStrategy implements SortStrategy {
    private final boolean odd;

    public NameLengthSortStrategy(boolean odd) {
        this.odd = odd;
    }

    @Override
    public List<UserEntity> sort(List<UserEntity> users) {
        return users.stream()
                .filter(user -> (user.getName().length() % 2 == 1) == odd)
                .sorted(Comparator.comparing(user -> user.getName().length()))
                .collect(Collectors.toList());
    }
}

