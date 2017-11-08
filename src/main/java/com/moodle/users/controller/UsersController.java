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
package com.moodle.users.controller;

import com.moodle.users.model.Error;
import com.moodle.users.model.User;
import com.moodle.users.model.UserDTO;
import com.moodle.users.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@EnableWebMvc
public class UsersController {

    private UsersService usersService;

    private Logger log = LoggerFactory.getLogger(UsersController.class);
    private static final String ERROR = "Missing Required Fields";

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(path = "/v1/users", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserDTO user) {
        try {
            if(user == null){
                //if either don't exist send unprocessable entity request
                log.warn("Null user, returning error");

                return new ResponseEntity(new Error(ERROR), getCorsHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            log.debug("Received request {}", user);

            //check to ensure first and last name exist
            if (user.getFirstName() == null || user.getLastName() == null || user.getCompanyName() == null) {

                //if either don't exist send unprocessable entity request
                log.warn("First or Last name missing form request, returning 4XX error");

                return new ResponseEntity(new Error(ERROR), getCorsHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
            }

            //Save user
            User savedUser = usersService.save(user);

            log.info("New User created {} returning", savedUser);
            //return success
            return new ResponseEntity(savedUser, getCorsHeaders(), HttpStatus.CREATED);
        }

        catch (Exception e){
            log.error("Error processing request ", e);
            return new ResponseEntity(new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
                    getCorsHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Helper method to create cors headers for Browsers
     * @return
     */
    private MultiValueMap<String, String> getCorsHeaders(){
        MultiValueMap headers = new LinkedMultiValueMap<String, String>();
        headers.set( "Access-Control-Allow-Origin", "*");
        return headers;
    }

}
