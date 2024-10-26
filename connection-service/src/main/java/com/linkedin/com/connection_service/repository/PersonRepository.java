package com.linkedin.com.connection_service.repository;

import com.linkedin.com.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Optional<Person> getByName(String name);

//    @Query("MATCH (personA:Person) -[:CONNECTED_TO]- (personB:Person) "+
//            "WHERE personA.userId = $userId RETURN personB")
    @Query("MATCH (personA:Person)-[:ACTED_IN]->(personB:Movie)\n" +
            "WHERE personA.name = \"Kelly McGillis\"\n" +
            "RETURN personB")
    List<Person> getFirstDegreeConnection(Long userId);
}
