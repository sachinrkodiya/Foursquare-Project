package com.megaProject.Application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.megaProject.Application.model.Place;



public interface PlaceRepository extends JpaRepository<Place, Integer> {

	Optional<Place> findByName(String name);

	Optional<Place> findById(int id);
	
	

	@Query(value = "SELECT * FROM bootdb.place where id=?1" ,nativeQuery = true)
	Place findByPlaceId(int placeId);
	
	

	@Query(value = "SELECT * FROM bootdb.place where landmark=?1" ,nativeQuery = true)
	List<Place> findByLandmark(String landmark);
	
	
	@Query(value = "SELECT * FROM bootdb.place where landmark=?1 ORDER BY overall_rating DESC" ,nativeQuery = true)
	List<Place> findByPopular(String landmark);
	
	@Query(value = "SELECT * FROM bootdb.place  ORDER BY overall_rating DESC" ,nativeQuery = true)
	List<Place> sortByrating(List<Place> place);
	


}