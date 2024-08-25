
package com.hotelrents.Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToMany(mappedBy = "roles")
	private Collection<User> user = new HashSet<>();
	
	
	
	
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getName() {
		return name!=null ? name : ""  ;
	}



	public void setName(String name) {
		this.name = name;
	}




	public Collection<User> getUser() {
		return user;
	}




	public void setUser(Collection<User> user) {
		this.user = user;
	}




	public void asignRoleToUser(User user) {
		user.getRoles().add(this);
		this.getUser().add(user);
	}
	
	
	public void removeUserFromRole(User user) {
		user.getRoles().remove(this);
		this.getUser().remove(user);
	}
	
	public void removeAllUsersFromRole() {
		if(this.getUser()!= null) {
			List<User> roleUsers = this.getUser().stream().toList();
			roleUsers.forEach(this::removeUserFromRole);
		}
	}




	public Role(String name) {
		super();
		this.name = name;
	}
	
	
	
	

	
}
