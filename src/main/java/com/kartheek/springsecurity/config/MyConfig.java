package com.kartheek.springsecurity.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
