package com.megaProject.Application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.megaProject.Application.model.ReviewRating;

public interface ReviewRatingRepository extends JpaRepository<ReviewRating, Long> {
	

	@Query(value = "SELECT * FROM bootdb.review_rating where user_id=?1 and place_id=?2" ,nativeQuery = true)
	ReviewRating findTheRating(long userId, int placeId);
	
	
	@Query(value = "SELECT * FROM bootdb.review_rating where place_id=?1 and rating != 0" ,nativeQuery = true)
	List<ReviewRating> ListOfRating(int placeId);
	
	
	@Query(value = "SELECT * FROM bootdb.review_rating where place_id=?1" ,nativeQuery = true)
	Page<ReviewRating> findByReview(int placeId,Pageable paging);

}
