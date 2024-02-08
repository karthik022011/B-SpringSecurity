package com.kartheek.springsecurity.auth.service;

import com.kartheek.springsecurity.auth.dto.request.RegisterReqDTO;
import com.kartheek.springsecurity.auth.entity.RegisterUser;
import com.kartheek.springsecurity.auth.repository.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private ModelMapper modelMapper;
    private UserInfoRepository userInfoRepository;
    @Override
    public RegisterReqDTO createUser(RegisterReqDTO registerReqDTO) {
        RegisterUser user = modelMapper.map(registerReqDTO, RegisterUser.class);
        RegisterUser savedUser = userInfoRepository.save(user);
        return modelMapper.map(user,RegisterReqDTO.class);
    }
}
