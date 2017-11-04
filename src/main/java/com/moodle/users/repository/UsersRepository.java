package com.moodle.users.repository;

import com.moodle.users.model.User;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public interface UsersRepository {

    User save(User user);
}
