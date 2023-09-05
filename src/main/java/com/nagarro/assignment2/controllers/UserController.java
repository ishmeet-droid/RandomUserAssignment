package com.nagarro.assignment2.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.assignment2.exceptions.CustomException;
import com.nagarro.assignment2.model.UserEntity;
import com.nagarro.assignment2.repositories.UserRepo;
import com.nagarro.assignment2.services.UserService;
import com.nagarro.assignment2.utils.RandomUser;
import com.nagarro.assignment2.utils.validator.Validator;
import com.nagarro.assignment2.utils.validator.ValidatorFactory;

@RestController
@RequestMapping("/")
public class UserController {

	@Autowired
	private WebClient webClient;
	// @Autowired
	// RandomUser user;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;



	@PostMapping("user")
	public ResponseEntity<List<UserEntity>> createUser(@RequestBody Map<String, Integer> size) throws CustomException {

		RandomUser user = new RandomUser(webClient);
		List<UserEntity> listOfUsers = new ArrayList<>();

		UserEntity res = null;
		int len = size.get("size");
		if (len >= 1 && len <= 5) {

			for (int i = 0; i < len; i++) {

				res = user.getRandomUser();

				listOfUsers.add(res);

				userRepo.save(res);

			}
		} else {
			throw new CustomException("size max 5 min 1");
		}
		return new ResponseEntity<List<UserEntity>>(listOfUsers, HttpStatus.CREATED);

	}

	@GetMapping("user")
	public ResponseEntity<List<UserEntity>> getUser(
			@RequestParam(required = false) String sortType,
			@RequestParam(required = false) String sortOrder,
			@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "0") Integer offset) {

		Validator<?> validator;


		List<UserEntity> users;

		if (sortType == null && sortOrder == null) {
			users = userService.getUsers();
		} else {
			// You can use the provided query parameters to filter and sort the list of
			// users
		validator = ValidatorFactory.getValidator(limit);
			
        if (!validator.isValid(limit)) {
            return ResponseEntity.badRequest().build();
        }
		
		validator = ValidatorFactory.getValidator(sortType);

        if (!validator.isValid(sortType)) {
            return ResponseEntity.badRequest().build();
        }
		validator = ValidatorFactory.getValidator(limit);
			
        if (!validator.isValid(limit)) {
            return ResponseEntity.badRequest().build();
        }
		
		validator = ValidatorFactory.getValidator(sortOrder);

        if (!validator.isValid(sortOrder)) {
            return ResponseEntity.badRequest().build();
        }
			users = userService.getUsers(sortType, sortOrder, limit, offset);
			// List<UserEntity> users = userRepo.findAll();
		}

		return new ResponseEntity<List<UserEntity>>(users, HttpStatus.ACCEPTED);

	}

}
