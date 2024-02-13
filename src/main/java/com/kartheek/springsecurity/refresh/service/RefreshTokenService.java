package com.kartheek.springsecurity.refresh.service;

import com.kartheek.springsecurity.auth.repository.UserInfoRepository;
import com.kartheek.springsecurity.refresh.entity.RefreshToken;
import com.kartheek.springsecurity.refresh.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${app.jwt-refresh-expiration-milliseconds}")
    private long jwtRefreshExpDate;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserInfoRepository userRepository;

    public RefreshToken createRefreshToken(String userName){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRegisterUser(userRepository.findByEmail(userName).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpDate));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}

