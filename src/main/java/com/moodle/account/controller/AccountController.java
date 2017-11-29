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
package com.moodle.account.controller;

import com.moodle.account.model.Error;
import com.moodle.account.model.Account;
import com.moodle.account.model.AccountDTO;
import com.moodle.account.service.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
public class AccountController {

    private AccountsService accountsService;

    private Logger log = LoggerFactory.getLogger(AccountController.class);
    private static final String ERROR = "Missing Required Fields";

    @Autowired
    public AccountController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @RequestMapping(path = "/v1/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody AccountDTO account) {
        try {
            if(account == null){
                //if either don't exist send unprocessable entity request
                log.warn("Null account, returning error");

                return new ResponseEntity(new Error(ERROR), getCorsHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            log.debug("Received request {}", account);

            //check to ensure first and last name exist
            if (account.getFirstName() == null || account.getLastName() == null || account.getCompanyName() == null) {

                //if either don't exist send unprocessable entity request
                log.warn("First or Last name missing form request, returning 4XX error");

                return new ResponseEntity(new Error(ERROR), getCorsHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
            }

            //Save account
            Account savedAccount = accountsService.save(account);

            log.info("New account created {} returning", savedAccount);
            //return success
            return new ResponseEntity(savedAccount, getCorsHeaders(), HttpStatus.CREATED);
        }

        catch (Exception e){
            log.error("Error processing request ", e);
            return new ResponseEntity(new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
                    getCorsHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @RequestMapping(path = "/v1/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccountByEmail(@RequestParam("email") Optional<String> email) {
        try {

            log.debug("Request received for email {} ", email);
            //if email not present return error
            if (!email.isPresent()) {
                log.debug("email not present, returning error");
                return new ResponseEntity(new Error(ERROR), getCorsHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);

            }
            //otherwise go get them emails.  We don't care if none exist, we'll just return an empty array
            Account account = accountsService.getAccount(email.get());
            log.info("Fetched account {} ", account);
            //return
            return new ResponseEntity(account, getCorsHeaders(), HttpStatus.OK);
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
