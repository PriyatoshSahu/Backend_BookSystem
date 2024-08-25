//package com.hotelrents.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.hotelrents.Exception.UserAlreadyExistsException;
//import com.hotelrents.Model.User;
//import com.hotelrents.Service.UserService;
//
//public class AuthController {
//	
//	@Autowired
//	private UserService userService;
//	
//	@PostMapping("/register-user")
//	public ResponseEntity<?> registerUser(User user){
//		try {
//			 userService.registerUser(user);
//			 return ResponseEntity.ok("register successfully");
//		}
//		catch(UserAlreadyExistsException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//		}
//	}
//
//}
