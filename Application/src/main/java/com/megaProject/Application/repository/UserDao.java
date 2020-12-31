package com.megaProject.Application.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.megaProject.Application.model.DAOUser;


@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
	@Query(value = "SELECT * FROM bootdb.user WHERE email = ?1", nativeQuery = true)
	DAOUser findByEmail(String email);
	
	@Query(value = "SELECT * FROM bootdb.user WHERE id = ?1", nativeQuery = true)
	DAOUser findByUserID(long userId);
	
	Boolean existsByEmail(String email);

	
	
}