package com.megaProject.Application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megaProject.Application.model.PlaceType;



@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {

	Optional<PlaceType> findByName(String string);

}