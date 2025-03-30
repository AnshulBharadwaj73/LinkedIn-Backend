package com.linkedin.com.posts_service.service;

import com.linkedin.com.posts_service.clients.ConnectionsClient;
import com.linkedin.com.posts_service.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionPostService {

    private final ConnectionsClient connectionsClient;

    public List<PersonDto> getAllConnection(){
        return connectionsClient.getFirstConnections();
    }
}
