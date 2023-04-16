package com.project.questapp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.questapp.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	RefreshToken findByUserId(Long userId);
	
}
