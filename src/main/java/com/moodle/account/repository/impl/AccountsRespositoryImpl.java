package com.moodle.account.repository.impl;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.moodle.account.model.Account;
import com.moodle.account.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by andrewlarsen on 11/4/17.
 */
@Repository
public class AccountsRespositoryImpl implements AccountsRepository {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public AccountsRespositoryImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    @Override
    public Account save(Account account) {
        //set id
        account.setId(UUID.randomUUID().toString());
        //save
        dynamoDBMapper.save(account);
        //and return
        return account;
    }

    @Override
    public List<Account> getAccounts(String email) {
        Account account = new Account();
        account.setEmail(email);
        DynamoDBQueryExpression<Account> queryExpression = new DynamoDBQueryExpression<Account>()
                .withHashKeyValues(account);
        return dynamoDBMapper.query(Account.class, queryExpression);

    }
}
