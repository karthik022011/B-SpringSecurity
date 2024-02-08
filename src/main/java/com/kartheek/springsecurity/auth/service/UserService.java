package com.kartheek.springsecurity.auth.service;

import com.kartheek.springsecurity.auth.dto.request.RegisterReqDTO;
import com.kartheek.springsecurity.auth.entity.RegisterUser;

public interface UserService {
    RegisterReqDTO createUser(RegisterReqDTO registerReqDTO);
}
