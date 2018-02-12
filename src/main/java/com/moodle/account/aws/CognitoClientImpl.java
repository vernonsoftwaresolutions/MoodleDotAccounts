package com.moodle.account.aws;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.moodle.account.LambdaHandler;
import com.moodle.account.mapper.CognitoUserMapper;
import com.moodle.account.model.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by andrewlarsen on 2/11/18.
 */
@Component
public class CognitoClientImpl implements CognitoClient {
    private Logger log = LoggerFactory.getLogger(LambdaHandler.class);

    private AWSCognitoIdentityProvider provider;
    private CognitoUserMapper mapper;

    @Autowired
    public CognitoClientImpl(AWSCognitoIdentityProvider provider, CognitoUserMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public AccountDTO getUser(String token) {
        log.debug("About to make request to retieve user with token {} ", token);

        GetUserResult result = provider.getUser(new GetUserRequest()
                .withAccessToken(token));

        log.debug("Returned result {} ", result);

        AccountDTO cognitoUser = mapper.from(result);

        return cognitoUser;

    }
}
