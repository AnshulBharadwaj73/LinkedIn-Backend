package com.linkedin.com.user_service.clients;


import com.linkedin.com.user_service.dto.PersonDto;
import com.linkedin.com.user_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionsClient {

    @PostMapping("/core/person")
    public void addUserToConnectionService(@RequestBody PersonDto personDto);
}
