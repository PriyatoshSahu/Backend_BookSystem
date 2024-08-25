package com.hotelrents.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelrents.Model.User;

public interface UserRepo  extends JpaRepository<User, Integer> {
	
	boolean existsByEmail(String email);
	
	void deleteByEmail(String email);
	
	Optional<User> findByEmail(String email);
}
