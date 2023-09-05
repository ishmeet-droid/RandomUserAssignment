package com.nagarro.assignment2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.assignment2.model.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

}


