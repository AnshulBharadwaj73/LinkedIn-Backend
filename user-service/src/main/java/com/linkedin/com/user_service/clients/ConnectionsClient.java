package com.linkedin.com.user_service.clients;


import com.linkedin.com.user_service.dto.PersonDto;
import com.linkedin.com.user_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "connections-service", path = "/connections")
public interface  ConnectionsClient {

    @PostMapping("/core/person")
    public Boolean addUserToConnectionService(@RequestBody PersonDto personDto);

    @DeleteMapping("/core/deletePerson/{userId}")
    public void deleteUserToConnectionService(@PathVariable Long userId);


}
