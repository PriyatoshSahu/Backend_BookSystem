package com.hotelrents.Service;

import java.util.List;

import com.hotelrents.Model.Role;
import com.hotelrents.Model.User;

public interface RoleService {
	List<Role> getRole();
	
	Role createRole(Role theRole);
	
	void deleteRole(int id);
	
	Role findByName(String name);
	
	User removeUserFromRole(int usserid, int roleid);
	
	User asignRoleToUser(int userid , int roleid);
	
	Role removeAllUsersFromRole(int roleid);
}
