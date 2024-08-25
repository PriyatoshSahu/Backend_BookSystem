package com.hotelrents.Controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelrents.Model.User;
import com.hotelrents.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/all-user")
public ResponseEntity<List<User>> getUser(){
	return new ResponseEntity<>(userService.getUsers(),HttpStatus.FOUND);
}


	@GetMapping("/{email}")
public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
	try {
		User theUser = userService.getUser(email);
		return ResponseEntity.ok(theUser);
		}
	catch(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
		
	}
	
	
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUserByEmail(@PathVariable("userId") String email){
	try {
		userService.deleteUser(email);
		return ResponseEntity.ok("user deleted successfully");
	}
	catch(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	}


}

	
