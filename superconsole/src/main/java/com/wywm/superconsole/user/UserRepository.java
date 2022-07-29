package com.wywm.superconsole.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Data Access Object Layer - Contains logic that performs CRUD operations
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);


}