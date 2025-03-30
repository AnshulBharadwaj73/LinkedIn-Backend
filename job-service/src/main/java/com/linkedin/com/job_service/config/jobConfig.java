package com.linkedin.com.job_service.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class jobConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
