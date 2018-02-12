package com.moodle.account.mapper;

import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.moodle.account.TestUtil;
import com.moodle.account.model.AccountDTO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by andrewlarsen on 2/11/18.
 */
public class CognitoUserMapperTest {
    GetUserResult result;
    CognitoUserMapper mapper;
    @Before
    public void setup() throws IOException {
        result = TestUtil.createGetUserResult();
        mapper = new CognitoUserMapper();
    }
    @Test
    public void fromAddress() throws Exception {
        AccountDTO user = mapper.from(result);
        assertEquals(user.getAddress(), TestUtil.address);
    }
    @Test
    public void fromName() throws Exception {
        AccountDTO user = mapper.from(result);
        assertEquals(user.getName(), TestUtil.name);
    }
    @Test
    public void fromUserName() throws Exception {
        AccountDTO user = mapper.from(result);
        assertEquals(user.getUserName(), TestUtil.userName);
    }
    @Test
    public void fromEmail() throws Exception {
        AccountDTO user = mapper.from(result);
        assertEquals(user.getEmail(), TestUtil.email);
    }
    @Test
    public void fromPhoneNumber() throws Exception {
        AccountDTO user = mapper.from(result);
        assertEquals(user.getPhoneNumber(), TestUtil.phoneNumber);
    }
}