package com.megaProject.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.megaProject.Application.model.ReviewPhotos;
@Repository
public interface PhotosRepository extends JpaRepository<ReviewPhotos,Long> {
	
	@Query(value = "SELECT * FROM bootdb.review_photos where place_id=?1", nativeQuery = true)
	List<ReviewPhotos> getPhotosByPlaceId(int placeId);

	@Query(value = "SELECT user_id FROM bootdb.review_photos where place_id=?1", nativeQuery = true)
	List<ReviewPhotos> getUsersByPlaceId(int placeId);

	@Query(value = "SELECT * FROM bootdb.review_photos where photo_id=?1", nativeQuery = true)
	ReviewPhotos getPhotoByPhotoId(long photo_id);

}
