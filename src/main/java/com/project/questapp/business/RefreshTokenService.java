package com.project.questapp.business;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.questapp.dataAccess.RefreshTokenRepository;
import com.project.questapp.entities.RefreshToken;
import com.project.questapp.entities.User;

@Service
public class RefreshTokenService {

	@Value("${refresh.token.expires.in}")
	Long expireSecond;
	
	private RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	
	public boolean isRefreshExpired(RefreshToken token) {
		return token.getExpirationDate().before(new Date());
	}
	
	public String createRefreshToken(User user) {
		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpirationDate(Date.from(Instant.now().plusSeconds(expireSecond)));
		refreshTokenRepository.save(token);
		return token.getToken();
	}


	public RefreshToken getByUser(Long userId) {
		return refreshTokenRepository.findByUserId(userId);
	}
	
}
