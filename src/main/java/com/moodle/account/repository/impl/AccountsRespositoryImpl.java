package com.moodle.account.repository.impl;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.moodle.account.model.Account;
import com.moodle.account.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
    public Optional<Account> getAccount(String email) {
        Account account =  dynamoDBMapper.load(Account.class, email);

        if(account == null){
            return Optional.empty();
        }

        return Optional.of(account);

    }

    public Optional<Account> getAccountById(String id){
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1",  new AttributeValue().withS(id));

        DynamoDBQueryExpression<Account> queryExpression = new DynamoDBQueryExpression<Account>()
                //todo-factor this out
                .withIndexName("id-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(eav);

        List<Account> accountList =  dynamoDBMapper.query(Account.class, queryExpression);
        if(accountList.size() > 1){
            throw new IllegalArgumentException("Invalid number of accounts for id");
        }
        if(accountList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(accountList.get(0));
    }

    @Override
    public void deleteAccount(Account account) {
        dynamoDBMapper.delete(account);
    }
}
