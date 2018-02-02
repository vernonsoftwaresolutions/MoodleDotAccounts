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

import javax.swing.text.html.Option;
import java.util.Optional;


/**
 * Created by andrewlarsen on 11/4/17.
 */
@Service
public class AccountsService {

    private Logger log = LoggerFactory.getLogger(AccountsService.class);

    private AccountsRepository accountsRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }


    /**
     * Method to save account to backend persistence
     * @param account
     * @return
     */
    public Account save(AccountDTO account){
        //check if account exists for email
        if(accountsRepository.getAccount(account.getEmail()).isPresent()) {
            log.info("Account already exists for given email");
            throw new IllegalStateException("Account exists for email");
        }
        //first save the account into the account database
        log.info("Saving account to db {}", account);
        Account savedAccount = accountsRepository.save(createAccount(account));
        return savedAccount;


    }

    /**
     * Method to retrieve accounts by email
     * @param email
     * @return
     */
    public Optional<Account> getAccount(String email){
        return accountsRepository.getAccount(email);
    }

    public Optional<Account> getAccountById(String id){
        return accountsRepository.getAccountById(id);
    }

    /**
     * Method to delete account by account id
     * @param dto
     */
    public void deleteAccount(AccountDTO dto, String id){
        Account account = createAccount(dto);
        account.setId(id);
        accountsRepository.deleteAccount(account);
    }

    /**
     * Helper method to convert DTO to entity
     * @param accountDTO
     * @return
     */
    private Account createAccount(AccountDTO accountDTO){
        Account account = new Account();
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setEmail(accountDTO.getEmail());
        account.setPhoneNumber(accountDTO.getPhoneNumber());
        account.setCompanyName(accountDTO.getCompanyName());
        account.setLocation(accountDTO.getLocation());
        return account;
    }



}
