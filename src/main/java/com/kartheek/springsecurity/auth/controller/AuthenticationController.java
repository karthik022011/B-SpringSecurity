package com.kartheek.springsecurity.auth.controller;

import com.kartheek.springsecurity.auth.dto.request.LoginReqDTO;
import com.kartheek.springsecurity.auth.dto.request.RegisterReqDTO;
import com.kartheek.springsecurity.auth.service.UserService;
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

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<RegisterReqDTO> createUser(@RequestBody RegisterReqDTO registerReqDTO){
        RegisterReqDTO savedUser = userService.createUser(registerReqDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginReqDTO loginReqDTO){
        //If user present then only give token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDTO.getUserName(),loginReqDTO.getPassword())
        );
        if(authentication.isAuthenticated()){
            String token = jwtTokenUtil.generateToken(loginReqDTO.getUserName());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }else{
            throw new UsernameNotFoundException("Invalid user details");
        }
    }
}
