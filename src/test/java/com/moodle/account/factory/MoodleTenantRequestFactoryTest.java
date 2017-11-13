package com.moodle.account.factory;

import com.moodle.account.model.Account;
import com.moodle.account.model.moodle.MoodleTenantRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrewlarsen on 11/10/17.
 */
public class MoodleTenantRequestFactoryTest {

    private Account account;
    private MoodleTenantRequestFactory factory = new MoodleTenantRequestFactory();
    @Before
    public void setup(){
        account = new Account();
        account.setCompanyName("COMPANY");
        account.setId("ID");
    }

    @Test
    public void createRequestId() throws Exception {
        MoodleTenantRequest request = factory.createRequest(account);
        assertEquals(request.getId(), account.getId());
    }

    @Test
    public void createRequestCompanyName() throws Exception {
        MoodleTenantRequest request = factory.createRequest(account);
        assertEquals(request.getClientName(), account.getCompanyName());
    }
    @Test
    public void createRequestPriority() throws Exception {
        MoodleTenantRequest request = factory.createRequest(account);
        assertNotNull(request.getPriority());
    }
}