package com.hotelrents.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelrents.Model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(String name);
	
	boolean existsByName(String role);
}
