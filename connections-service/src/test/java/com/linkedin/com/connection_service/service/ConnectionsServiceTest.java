package com.linkedin.com.connection_service.service;

import com.linkedin.com.connection_service.auth.UserContextHolder;
import com.linkedin.com.connection_service.entity.Person;
import com.linkedin.com.connection_service.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ConnectionsServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ConnectionsService connectionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Test to get first degree connections")
    void getFirstDegreeConnections() {

            Long mockUserId = 1L;
            List<Person> mockConnections = Arrays.asList(new Person(), new Person());
            mockStatic(UserContextHolder.class);
            when(UserContextHolder.getCurrentUserId()).thenReturn(mockUserId);
            when(personRepository.getFirstDegreeConnection(mockUserId)).thenReturn(mockConnections);

            // Act
            List<Person> result = connectionsService.getFirstDegreeConnections();

            // Assert
            assertEquals(mockConnections, result);
            verify(personRepository, times(1)).getFirstDegreeConnection(mockUserId);
        }
}