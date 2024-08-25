package com.hotelrents.Service;

import java.util.List;

import com.hotelrents.Model.User;

public interface UserService {
	
	User registerUser(User user);
	
	List<User> getUsers();
	
	void deleteUser(String email);
	
	User getUser(String email);
	
}
