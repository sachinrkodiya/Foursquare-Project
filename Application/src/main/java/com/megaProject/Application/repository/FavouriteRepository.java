package com.megaProject.Application.repository;



import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megaProject.Application.model.Favourite;


@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Long>  {
	
	
	
	
	@Query(value = "SELECT * FROM bootdb.user_favourite where user_id = ?1", nativeQuery = true)
	Page<Favourite> findFavourite(long userId,Pageable paging);
	 List<Favourite> findFavourite(long userId);
	
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM bootdb.user_favourite where user_id = ?1 and place_id = ?2", nativeQuery = true)
	 int deleteFavourite(long userId, int placeId);

}
