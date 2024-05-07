package com.me.user.service.services;

import java.util.List;

import com.me.user.service.entities.User;

public interface UserService {

	//Create User
	User saveUser(User user);
	
	//get all users
	List<User>getAllUsers();
	
	//get single user by id
	User getUser(String userId);
	
	//update User
	//Delete User
}
