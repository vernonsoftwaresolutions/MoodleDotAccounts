package com.moodle.account.service;

import com.moodle.account.client.MoodleTenantClient;
import com.moodle.account.factory.MoodleTenantRequestFactory;
import com.moodle.account.model.Account;
import com.moodle.account.model.AccountDTO;
import com.moodle.account.model.moodle.MoodleTenantRequest;
import com.moodle.account.model.moodle.SQSResponse;
import com.moodle.account.repository.AccountsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;
    @Mock
    private MoodleTenantClient client;
    @Mock
    private MoodleTenantRequestFactory factory;

    private AccountsService service;
    private AccountDTO accountDTO;
    private Account account;
    private MoodleTenantRequest request;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new AccountsService(accountsRepository,client,factory);
        accountDTO = new AccountDTO();
        request = new MoodleTenantRequest();

    }
    @Test
    public void save() throws Exception {
        given(accountsRepository.save(anyObject())).willReturn(account);
        given(client.postMessage(any())).willReturn(new SQSResponse());
        given(factory.createRequest(account)).willReturn(request);
        assertEquals(service.save(accountDTO), account) ;
    }
    @Test
    public void saveCompanyName() throws Exception {
        given(accountsRepository.save(anyObject())).willReturn(account);
        given(client.postMessage(any())).willReturn(new SQSResponse());
        given(factory.createRequest(account)).willReturn(request);
        assertEquals(service.save(accountDTO), account) ;
    }
}