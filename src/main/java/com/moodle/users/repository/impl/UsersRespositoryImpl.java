package com.moodle.users.repository.impl;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.moodle.users.model.User;
import com.moodle.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by andrewlarsen on 11/4/17.
 */
@Repository
public class UsersRespositoryImpl implements UsersRepository {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UsersRespositoryImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    @Override
    public User save(User user) {
        //set id
        user.setId(UUID.randomUUID().toString());
        //save
        dynamoDBMapper.save(user);
        //and return
        return user;
    }
}
