package com.moodle.users.service;

import com.moodle.users.model.User;
import com.moodle.users.model.UserDTO;
import com.moodle.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andrewlarsen on 11/4/17.
 */
@Service
public class UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Method to save user to backend persistence
     * @param user
     * @return
     */
    public User save(UserDTO user){
        return usersRepository.save(createUser(user));
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
        return user;
    }

}
