package com.hotelrents.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelrents.Exception.RoleAlreadyExistException;
import com.hotelrents.Model.Role;
import com.hotelrents.Model.User;
import com.hotelrents.Service.RoleService;

public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@GetMapping("/all")
	public ResponseEntity<List<Role>> getAllRole(){
		return new ResponseEntity<>(roleService.getRole(),HttpStatus.FOUND);
	}
	
	
	@PostMapping("/createrole")
	public ResponseEntity<String> createRole(Role theRole){
		try {
			roleService.createRole(theRole);
			return ResponseEntity.ok("new role created successfully");
		}
		catch(RoleAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/delete/{roleid}")
	public void deleteRole(@PathVariable("roleid") int roleid ) {
		roleService.deleteRole(roleid);
	}
	
	
	@PostMapping("/removeallusers/{roleid}")
	public Role removeAllUserFromRole(@PathVariable("roleid") int roleid) {
		return roleService.removeAllUsersFromRole(roleid);
	}
	
	@PostMapping("/remove-user-from-role")
	public User removeUserFromRole(@RequestParam ("userid") int userid , @RequestParam ("roleid") int roleid) {
		return roleService.removeUserFromRole(userid, roleid);
	}
	
	@PostMapping("/aasign-role")
	public User assignRole(@RequestParam("userid")  int userid , @RequestParam ("roleid") int roleid) {
		return roleService.asignRoleToUser(userid, roleid);
	}
}
