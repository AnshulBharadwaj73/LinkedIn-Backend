//package com.linkedin.com.user_profile.repository;
//
//import com.linkedin.com.user_profile.entity.User;
//import com.mongodb.ReadConcern;
//import com.mongodb.ReadPreference;
//import com.mongodb.TransactionOptions;
//import com.mongodb.WriteConcern;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Repository;
//
//
//@Repository
//public class MongoUser implements UserRepository{
//
//    private static final TransactionOptions txnOptions = TransactionOptions.builder()
//            .readPreference(ReadPreference.primary())
//            .readConcern(ReadConcern.MAJORITY)
//            .writeConcern(WriteConcern.MAJORITY)
//            .build();
//    private final MongoClient client;
//    private MongoCollection<User> userMongoCollection;
//
//    public MongoUser(MongoClient mongoClient) {
//        this.client = mongoClient;
//    }
//
//    @PostConstruct
//    void init() {
//        userMongoCollection = client.getDatabase("user-profile").getCollection("profile", User.class);
//    }
//    @Override
//    public User save(User user) {
////        user.setId(1L);
//        userMongoCollection.insertOne(user);
//        return user;
//    }
//}
