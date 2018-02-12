package com.moodle.account.aws;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.moodle.account.mapper.CognitoUserMapper;
import com.moodle.account.model.AccountDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;

/**
 * Created by andrewlarsen on 2/11/18.
 */

public class CognitoClientTest {
    private CognitoClient cognitoClient;
    @Mock
    private CognitoUserMapper mapper;
    @Mock
    private AWSCognitoIdentityProvider provider;

    private String token = "TOKEN";
    private GetUserResult result;
    private AccountDTO user;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        cognitoClient = new CognitoClientImpl(provider, mapper);
        result = new GetUserResult();
        user = new AccountDTO();
    }
    @Test
    public void getUser() throws Exception {
        given(provider.getUser(anyObject())).willReturn(result);
        given(mapper.from(result)).willReturn(user);
        AccountDTO result = cognitoClient.getUser(token);
        assertEquals(result, user);

    }


}