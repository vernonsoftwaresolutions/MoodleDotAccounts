/*
 * Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.moodle.users;

import com.moodle.users.model.Error;
import com.moodle.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@RestController
@EnableWebMvc
public class UsersController {
    private Logger log = LoggerFactory.getLogger(UsersController.class);
    private static final String ERROR = "Missing Required Fields";

    @RequestMapping(path = "/dev/v1/users", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        log.debug("Received request {}", user);
        //check to ensure first and last name exist
        if(user.getFirstName() == null || user.getLastName() == null){
            //if either don't exist send unprocessable entity request
            log.warn("First or Last name missing form request, returning 4XX error");
            return new ResponseEntity(new Error(ERROR), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        //otherwise set id and return
        //this is hardcoded for now

        user.setId(UUID.randomUUID().toString());
        log.info("New User created {} returning", user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

}
