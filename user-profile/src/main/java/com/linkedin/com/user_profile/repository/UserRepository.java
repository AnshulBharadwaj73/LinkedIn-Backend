package com.linkedin.com.user_profile.repository;

import com.linkedin.com.user_profile.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends MongoRepository<User, String>{
    User findByFirstName(String firstName);

//    User save(User user);
}
