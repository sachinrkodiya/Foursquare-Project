package com.megaProject.Application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.megaProject.Application.model.Place;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

	Optional<Place> findByName(String name);

	Optional<Place> findById(int id);
	
	

	@Query(value = "SELECT * FROM bootdb.place where id=?1" ,nativeQuery = true)
	Place findByPlaceId(int placeId);
	
	

	@Query(value = "SELECT * FROM bootdb.place where landmark=?1" ,nativeQuery = true)
	Page<Place> findByLandmark(String landmark,Pageable paging);
	
	
	@Query(value = "SELECT * FROM bootdb.place where landmark=?1 ORDER BY overall_rating DESC" ,nativeQuery = true)
	Page<Place> findByPopular(String landmark,Pageable paging);
	
	@Query(value = "SELECT * FROM bootdb.place  ORDER BY overall_rating DESC" ,nativeQuery = true)
	List<Place> sortByrating(List<Place> place);
	


}