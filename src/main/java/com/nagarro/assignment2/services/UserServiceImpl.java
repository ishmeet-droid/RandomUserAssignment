package com.nagarro.assignment2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.assignment2.model.UserEntity;
import com.nagarro.assignment2.repositories.UserRepo;
import com.nagarro.assignment2.utils.sortIngTechnique.AgeSortStrategy;
import com.nagarro.assignment2.utils.sortIngTechnique.NameLengthSortStrategy;
import com.nagarro.assignment2.utils.sortIngTechnique.SortContext;
import com.nagarro.assignment2.utils.sortIngTechnique.SortStrategy;

@Service
public class UserServiceImpl implements UserService {

  
    private List<UserEntity> users = new ArrayList<>();

    @Autowired
    UserRepo userRepo;

    // public UserServiceImpl(){
    //    users = userRepo.findAll();
    // }
    @Override
   public List<UserEntity> getUsers(){

    users = userRepo.findAll();

    return users;
   }

    @Override
    public List<UserEntity> getUsers(String sortType, String sortOrder, int limit, int offset) {
        
        
        List<UserEntity> sortedUsers;

        users = userRepo.findAll();
       
        SortStrategy strategy;
        if ("age".equalsIgnoreCase(sortType)) {
            strategy = new AgeSortStrategy("even".equalsIgnoreCase(sortOrder));
        } else if ("name".equalsIgnoreCase(sortType)) {
            strategy = new NameLengthSortStrategy("odd".equalsIgnoreCase(sortOrder));
        } else {
            throw new IllegalArgumentException("Invalid sortType");
        }

        sortedUsers = new SortContext(strategy).executeSort(users);

        // Apply pagination using limit and offset
        int startIndex = Math.min(offset, sortedUsers.size());
        int endIndex = Math.min(offset + limit, sortedUsers.size());

        return sortedUsers.subList(startIndex, endIndex);
    }

    
}
