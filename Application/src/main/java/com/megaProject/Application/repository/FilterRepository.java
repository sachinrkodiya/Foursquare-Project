package com.megaProject.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megaProject.Application.model.Filter;




@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
	
	@Query(value = "SELECT * FROM bootdb.filter where place_id=?1" ,nativeQuery = true)
	Filter findByPlaceId(int placeId);

}
