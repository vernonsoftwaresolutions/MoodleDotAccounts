package com.moodle.account.service;

import com.moodle.account.client.MoodleTenantClient;
import com.moodle.account.factory.MoodleSiteRequestFactory;
import com.moodle.account.model.Account;
import com.moodle.account.model.AccountDTO;
import com.moodle.account.repository.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by andrewlarsen on 11/4/17.
 */
@Service
public class AccountsService {

    private Logger log = LoggerFactory.getLogger(AccountsService.class);

    private AccountsRepository accountsRepository;
    private MoodleTenantClient tenantClient;
    private MoodleSiteRequestFactory factory;


    @Autowired
    public AccountsService(AccountsRepository accountsRepository, MoodleTenantClient tenantClient, MoodleSiteRequestFactory factory) {
        this.accountsRepository = accountsRepository;
        this.tenantClient = tenantClient;
        this.factory = factory;
    }


    /**
     * Method to save account to backend persistence
     * @param account
     * @return
     */
    public Account save(AccountDTO account){
        //first save the account into the account database
        log.info("Saving account to db");
        Account savedAccount = accountsRepository.save(createaccount(account));
        return savedAccount;

    }

    /**
     * Method to retrieve accounts by email
     * @param email
     * @return
     */
    public Account getAccount(String email){
        return accountsRepository.getAccount(email);
    }

    /**
     * Helper method to convert DTO to entity
     * @param accountDTO
     * @return
     */
    private Account createaccount(AccountDTO accountDTO){
        Account account = new Account();
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setEmail(accountDTO.getEmail());
        account.setPhoneNumber(accountDTO.getPhoneNumber());
        account.setCompanyName(accountDTO.getCompanyName());
        return account;
    }



}
