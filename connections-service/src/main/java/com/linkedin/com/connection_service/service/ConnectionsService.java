package com.linkedin.com.connection_service.service;

import com.linkedin.com.connection_service.auth.UserContextHolder;
import com.linkedin.com.connection_service.dto.PersonDto;
import com.linkedin.com.connection_service.entity.Person;
import com.linkedin.com.connection_service.event.AcceptConnectionRequestEvent;
import com.linkedin.com.connection_service.event.SendConnectionRequestEvent;
import com.linkedin.com.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;

    public List<Person> getFirstDegreeConnections(){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting first g=degree connection for user with id: {}",userId);

        return personRepository.getFirstDegreeConnection(userId);
    }

    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Trying to send connection request, sender:{}, receiver :{} ",senderId,receiverId);

        if(senderId.equals(receiverId)){
            throw new RuntimeException(("Both sender and receiver cannot be same"));
        }

//        boolean alreadySentRequest  =personRepository.connectionRequestExists(senderId,receiverId);
//
//
//        if(alreadySentRequest){
//            throw new RuntimeException(("Connection request already exist, cannot send again"));
//        }
        log.info("sender id "+senderId+" receiver id "+receiverId);

        Long connectionRequestExists  =personRepository.connectionRequestExists(senderId,receiverId);
        log.info("sndkjhbffjhshfbhjfbj..........."+connectionRequestExists);
        if(connectionRequestExists!=0){
            throw new RuntimeException(("No connection request exits , cannot delete"));
        }


        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);

        if(alreadyConnected){
            throw new RuntimeException(( "Already exist users, cannot add send connection request"));
        }

        log.info("successfully sent the connection request");
        personRepository.addConnectionRequest(senderId,receiverId);

        SendConnectionRequestEvent sendConnectionRequestEvent=SendConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        sendRequestKafkaTemplate.send("send-connection-request-topic",sendConnectionRequestEvent);

        return true;
    }

    public Boolean acceptConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();

//        boolean connectionRequestExists  =personRepository.connectionRequestExists(senderId,receiverId);
//        if(!connectionRequestExists){
//            throw new RuntimeException(("No connection request exists to accept"));
//        }
        log.info("sender id "+senderId+" receiver id "+receiverId);

        Long connectionRequestExists  =personRepository.connectionRequestExists(senderId,receiverId);
        log.info("sndkjhbffjhshfbhjfbj..........."+connectionRequestExists);
        if(connectionRequestExists==0){
            throw new RuntimeException(("No connection request exits , cannot delete"));
        }


        personRepository.acceptConnectionRequest(senderId,receiverId);

        log.info("Successfully accept the connection request, sender: {}, receiver: {}",senderId,receiverId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent=AcceptConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        acceptRequestKafkaTemplate.send("accept-connection-request-topic",acceptConnectionRequestEvent);
        return true;
    }

    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        log.info("sender id "+senderId+" receiver id "+receiverId);

        Long connectionRequestExists  =personRepository.connectionRequestExists(senderId,receiverId);
        log.info("sndkjhbffjhshfbhjfbj..........."+connectionRequestExists);
        if(connectionRequestExists==0){
            throw new RuntimeException(("No connection request exits , cannot delete"));
        }

        personRepository.rejectConnectionRequest(senderId,receiverId);

        return true;
    }

    public Boolean addUserToConnectionService(PersonDto personDto) {
        Long userId= personDto.getUserId();
        String name = personDto.getName();


        personRepository.addUserToConnectionService(userId,name);
        return true;
    }

    public Boolean deleteUserWithUserId(Long userId) {
        personRepository.deleteUserWithUserId(userId);
        return true;
    }
}
