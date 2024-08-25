package com.hotelrents.Model;

import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastname;
	private String email;
	private String password;
	
	
//	@ManyToMany: This annotation establishes a many-to-many relationship between entities. In this case, each user can have multiple roles, and each role can be associated with multiple users.

//  fetch = FetchType.EAGER: This attribute specifies how the associated entities are loaded from the database.
//	With FetchType.EAGER, the associated roles will be loaded eagerly along with the user entity, meaning that 
//	when you fetch a user, its roles will also be fetched immediately.

//  cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}: This attribute defines the cascade 
//	behavior for operations performed on the User entity. With these cascade types:

//  CascadeType.PERSIST: When a new User entity is persisted (i.e., saved to the database), the associated Role entities will also be persisted.
//
//	CascadeType.MERGE: When a User entity is merged (i.e., updated in the database), the associated Role entities will also be merged.
//  
//	CascadeType.DETACH: When a User entity is detached (i.e., removed from the persistence context), the associated Role entities will also be detached.
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	
				@JoinTable(name = "user_roles" , 
	
				joinColumns = @JoinColumn(name="user_id", referencedColumnName ="id" ), 
	
				inverseJoinColumns = @JoinColumn(name="role_id" , referencedColumnName = "id") )
	
	private Collection<Role> roles = new HashSet<>();


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Collection<Role> getRoles() {
		return roles;
	}


	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
}
