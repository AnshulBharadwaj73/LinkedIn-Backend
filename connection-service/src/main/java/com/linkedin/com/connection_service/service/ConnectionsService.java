package com.linkedin.com.connection_service.service;

import com.linkedin.com.connection_service.entity.Person;
import com.linkedin.com.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(Long userId){
        log.info("Getting first g=degree connection for user with id: {}",userId);

        return personRepository.getFirstDegreeConnection(userId);
    }
}
