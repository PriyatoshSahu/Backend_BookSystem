package com.hotelrents.Service;

import java.util.Collections;   
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotelrents.Exception.UserAlreadyExistsException;
import com.hotelrents.Exception.UserNotFoundException;
import com.hotelrents.Model.Role;
import com.hotelrents.Model.User;
import com.hotelrents.Repository.RoleRepo;
import com.hotelrents.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	
	@Override
	public User registerUser(User user) {
		
	if(userRepo.existsByEmail(user.getEmail())) {
		throw new UserAlreadyExistsException( user.getEmail()+ "already exist ");
	}
//	user.setPassword(passwordEncoder.encode(user.getPassword()));
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	System.out.println(user.getPassword()); 
	Role userRole = roleRepo.findByName("ROLE_USER").get();
	user.setRoles(Collections.singletonList(userRole));
	return userRepo.save(user);
	}

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Transactional
	@Override
	public void deleteUser(String email) {
		User theuser = getUser(email);
		if(theuser!=null) {
		userRepo.deleteByEmail(email);
		}
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email).orElseThrow( ()-> new UserNotFoundException("user not found"));
	}
	

	
}
