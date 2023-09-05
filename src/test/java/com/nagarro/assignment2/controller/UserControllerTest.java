package com.nagarro.assignment2.controller;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.nagarro.assignment2.controllers.UserController;
import com.nagarro.assignment2.exceptions.CustomException;
import com.nagarro.assignment2.model.UserEntity;
import com.nagarro.assignment2.services.UserServiceImpl;
import com.nagarro.assignment2.utils.RandomUser;

@SpringBootTest

public class UserControllerTest {

    @MockBean
    private UserServiceImpl service;

    @Autowired
    private UserController controller;

    @MockBean 
    private RandomUser randomUser;

    UserEntity user1;
    UserEntity user2;
    UserEntity user3;

    @BeforeEach

    public void init() {

       user1 = UserEntity.builder()
                            .age(10)
                            .dateCreated(new Date())
                            .dateModified(new Date())
                            .gender("male")
                            .name("Ish")
                            .nationality("In")
                            .build();

        user2 =  UserEntity.builder()
                            .age(10)
                            .dateCreated(new Date())
                            .dateModified(new Date())
                            .gender("male")
                            .name("Ish2")
                            .nationality("In")
                            .build();


        user3 =  UserEntity.builder()
                            .age(10)
                            .dateCreated(new Date())
                            .dateModified(new Date())
                            .gender("male")
                            .name("Ish3")
                            .nationality("In")
                            .build();

    }

    @Test
    public void getUser() {

        List<UserEntity> users = Arrays.asList(user1, user2, user3);

        Mockito.when(service.getUsers("age","name",4,1)).thenReturn(users);

        ResponseEntity<List<UserEntity>> wantList = controller.getUser("age","name",4,1);
        
        // System.out.println(wantList.getStatusCode());
        Assertions.assertEquals(HttpStatus.ACCEPTED, wantList.getStatusCode());

    }


     
    @Test
    public void getUser2() {

        List<UserEntity> users = Arrays.asList(user1, user2, user3);

        Mockito.when(service.getUsers("age","name12",4,1)).thenReturn(users);

        ResponseEntity<List<UserEntity>> wantList = controller.getUser("age","name123",4,1);
        
        // System.out.println(wantList.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, wantList.getStatusCode());

    }

      
    @Test
    public void createUser() throws CustomException {

        // UserEntity users = Arrays.asList(user1, user2, user3);

        Mockito.when(randomUser.getRandomUser()).thenReturn(user1);
        Map<String,Integer>size = new HashMap<>();

        size.put("size",5);

        ResponseEntity<List<UserEntity>> wantList = controller.createUser(size);
        
        // System.out.println(wantList.getStatusCode());
        Assertions.assertEquals(HttpStatus.CREATED, wantList.getStatusCode());

    }

     @Test
    public void createUser2(){

        Mockito.when(randomUser.getRandomUser()).thenReturn(user1);
        Map<String,Integer>size = new HashMap<>();

        size.put("size",7);

        ResponseEntity<List<UserEntity>> wantList = new ResponseEntity<List<UserEntity>>(null, null, 0);
        try {
            wantList = controller.createUser(size);
        } catch (CustomException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // System.out.println(wantList.getStatusCode());
        

    }


    
    

}
