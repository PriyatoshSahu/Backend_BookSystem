package com.hotelrents.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hotelrents.Exception.RoleAlreadyExistException;
import com.hotelrents.Exception.UserAlreadyExistsException;
import com.hotelrents.Model.Role;
import com.hotelrents.Model.User;
import com.hotelrents.Repository.RoleRepo;
import com.hotelrents.Repository.UserRepo;

public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserService userService;
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public List<Role> getRole() {
		return roleRepo.findAll();
	}

	@Override
	public Role createRole(Role theRole) {
		String roleName = "ROLE_"+theRole.getName().toUpperCase();
		Role role = new Role(roleName);
		if (roleRepo.existsByName(roleName)) {
			throw new RoleAlreadyExistException(theRole.getName()+"role already exist");
		}
		return roleRepo.save(role);
	}

	
	
	@Override
	public void deleteRole(int id) {
		this.removeAllUsersFromRole(id);
		roleRepo.deleteById(id);	
	}
	
	

	@Override
	public Role findByName(String name) {
		return roleRepo.findByName(name).get();
	}
 
	
	
	@Override
	public User removeUserFromRole(int id, int roleid) {
		Optional<User> user = userRepo.findById(id);
		Optional<Role>role = roleRepo.findById(roleid);
		
		if(role.isPresent() && role.get().getUser().contains(user.get())) {
			role.get().removeUserFromRole(user.get());
			roleRepo.save(role.get());
			return user.get();
		}
		throw new UsernameNotFoundException("user not found");
	}

	
	
	@Override
	public User asignRoleToUser(int userid, int roleid) {
		Optional<User> user = userRepo.findById(userid);
		Optional<Role> role = roleRepo.findById(roleid);
		if(user.isPresent() && user.get().getRoles().contains(role.get())) {
			throw new UserAlreadyExistsException(user.get().getFirstName()+"is already assign to the"+ role.get().getName()+"role");
		}
		if(role.isPresent()) {
			role.get().asignRoleToUser(user.get());
			roleRepo.save(role.get());
		}
		return user.get();
	}

	
	
	@Override
	public Role removeAllUsersFromRole(int roleid) {
		Optional<Role> role = roleRepo.findById(roleid);
		role.get().removeAllUsersFromRole();
		return roleRepo.save(role.get());
	}

	

	
	

}
