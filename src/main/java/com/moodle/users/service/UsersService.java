package com.moodle.users.service;

import com.moodle.users.client.MoodleTenantClient;
import com.moodle.users.model.User;
import com.moodle.users.model.UserDTO;
import com.moodle.users.model.moodle.MoodleTenantRequest;
import com.moodle.users.model.moodle.SQSResponse;
import com.moodle.users.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by andrewlarsen on 11/4/17.
 */
@Service
public class UsersService {
    private static final String STACK_NAME = "moodle-ecs-single";
    private static final String VPC_ID = "vpc-c7aa77be";
    private static final String HOSTED_ZONE = "vssdevelopment.com";
    private Logger log = LoggerFactory.getLogger(UsersService.class);

    private UsersRepository usersRepository;
    private MoodleTenantClient tenantClient;


    @Autowired
    public UsersService(UsersRepository usersRepository, MoodleTenantClient tenantClient) {
        this.usersRepository = usersRepository;
        this.tenantClient = tenantClient;
    }

    /**
     * Method to save user to backend persistence
     * @param user
     * @return
     */
    public User save(UserDTO user){
        //first save the user into the user database
        log.info("Saving user to db");
        User savedUser = usersRepository.save(createUser(user));
        //next put the message on the queue
        //todo-need to address error situations
        SQSResponse response = tenantClient.postMessage(createRequest(user));

        return savedUser;

    }

    /**
     * Helper method to convert DTO to entity
     * @param userDTO
     * @return
     */
    private User createUser(UserDTO userDTO){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getFirstName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCompanyName(userDTO.getCompanyName());
        return user;
    }

    /**
     * Helper method to create Moodle Tenant Request
     * @param userDTO
     * @return
     */
    private MoodleTenantRequest createRequest(UserDTO userDTO){
        Random rand = new Random();

        int priority = rand.nextInt(100) + 1;
        MoodleTenantRequest request = new MoodleTenantRequest();
        request.setClientName(userDTO.getCompanyName());
        request.setPriority(priority);
        request.setHostedZoneName(HOSTED_ZONE);
        request.setStackName(STACK_NAME);
        request.setVpcId(VPC_ID);

        return request;
    }

}
