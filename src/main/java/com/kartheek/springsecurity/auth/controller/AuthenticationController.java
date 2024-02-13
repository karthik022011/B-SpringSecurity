package com.kartheek.springsecurity.auth.controller;

import com.kartheek.springsecurity.auth.dto.request.LoginReqDTO;
import com.kartheek.springsecurity.auth.dto.request.RegisterReqDTO;
import com.kartheek.springsecurity.auth.dto.response.LoginResDTO;
import com.kartheek.springsecurity.auth.service.UserService;
import com.kartheek.springsecurity.refresh.entity.RefreshToken;
import com.kartheek.springsecurity.refresh.model.RefreshTokenReqDTO;
import com.kartheek.springsecurity.refresh.model.RefreshTokenResDTO;
import com.kartheek.springsecurity.refresh.service.RefreshTokenService;
import com.kartheek.springsecurity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private JwtTokenProvider jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<RegisterReqDTO> createUser(@RequestBody RegisterReqDTO registerReqDTO){
        RegisterReqDTO savedUser = userService.createUser(registerReqDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> loginUser(@RequestBody LoginReqDTO loginReqDTO){
        //If user present then only give token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDTO.getUserName(),loginReqDTO.getPassword())
        );
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginReqDTO.getUserName());
            String accessToken = jwtTokenUtil.generateToken(loginReqDTO.getUserName());
            LoginResDTO loginResDTO = new LoginResDTO();
            loginResDTO.setAccessToken(accessToken);
            loginResDTO.setRefreshToken(refreshToken.getRefreshToken());
            return new ResponseEntity<>(loginResDTO, HttpStatus.OK);
        }else{
            throw new UsernameNotFoundException("Invalid user details");
        }
    }

    @ResponseBody
    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResDTO> refreshToken(@RequestBody RefreshTokenReqDTO refreshTokenReqDTO){
        return refreshTokenService.findByToken(refreshTokenReqDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getRegisterUser)
                .map(userInfo -> {
                    String accessToken = jwtTokenUtil.generateToken(userInfo.getEmail());
                    return ResponseEntity.ok(new RefreshTokenResDTO(accessToken,refreshTokenReqDTO.getToken()));
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
