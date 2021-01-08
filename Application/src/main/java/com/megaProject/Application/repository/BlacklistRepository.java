package com.megaProject.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.megaProject.Application.model.JwtBlackList;

public interface BlacklistRepository extends JpaRepository<JwtBlackList,Long>  {
	

}
