package com.me.user.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.user.service.entities.User;
import com.me.user.service.services.UserService;
import com.me.user.service.util.Constant;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping(Constant.USERSENDPOINT)
public class UserController {
	
	@Autowired
	UserService userService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	int retryCount = 1;
	
	//create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	
	//get all
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> allUsers = userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
	
	//get single 
	@GetMapping("/{userId}")
	@CircuitBreaker(name ="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	@Retry(name= "ratingHotelService", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String userId){
		
		logger.info("Retry count: {}", retryCount);
		retryCount++;
		
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		
//		logger.error("fallback is executed becasue service is down : {}", ex.getMessage());
		
		
		User user = User.builder().name("abc").email("test").build();
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	} 
	
}
