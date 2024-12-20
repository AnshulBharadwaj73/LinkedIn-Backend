package com.linkedin.com.connection_service.controller;

import com.linkedin.com.connection_service.dto.PersonDto;
import com.linkedin.com.connection_service.entity.Person;
import com.linkedin.com.connection_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections() {
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.sendConnectionRequest(userId));
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.acceptConnectionRequest(userId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.rejectConnectionRequest(userId));
    }

    @PostMapping("/person")
    public ResponseEntity<Boolean> addUserToConnectionService(@RequestBody PersonDto personDto){
        return ResponseEntity.ok(connectionsService.addUserToConnectionService(personDto));
    }

    @DeleteMapping("/deletePerson/{userId}")
    public ResponseEntity<Boolean> deleteUserToConnectionService(@PathVariable Long userId){

        return ResponseEntity.ok(connectionsService.deleteUserWithUserId(userId));
    }
}
