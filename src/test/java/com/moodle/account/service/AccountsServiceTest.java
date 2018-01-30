package com.moodle.account.service;

import com.moodle.account.client.MoodleTenantClient;
import com.moodle.account.factory.MoodleSiteRequestFactory;
import com.moodle.account.model.Account;
import com.moodle.account.model.AccountDTO;
import com.moodle.account.model.moodle.MoodleSiteRequest;
import com.moodle.account.model.moodle.SQSResponse;
import com.moodle.account.repository.AccountsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    private AccountsService service;
    private AccountDTO accountDTO;
    private Account account;
    private MoodleSiteRequest request;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new AccountsService(accountsRepository);
        accountDTO = new AccountDTO();
        request = new MoodleSiteRequest();
        accountDTO.setEmail("EMAIL");
        account = new Account();

    }
    @Test
    public void save() throws Exception {
        given(accountsRepository.getAccount(accountDTO.getEmail())).willReturn(Optional.empty());

        given(accountsRepository.save(anyObject())).willReturn(account);

        assertEquals(service.save(accountDTO), account) ;
    }
    @Test(expected = IllegalStateException.class)
    public void saveThrowsException() throws Exception {
        given(accountsRepository.getAccount(accountDTO.getEmail())).willReturn(Optional.of(account));

        given(accountsRepository.save(anyObject())).willReturn(account);

        assertEquals(service.save(accountDTO), account) ;
    }
    @Test
    public void saveCompanyName() throws Exception {
        given(accountsRepository.getAccount(accountDTO.getEmail())).willReturn(Optional.empty());

        given(accountsRepository.save(anyObject())).willReturn(account);

        assertEquals(service.save(accountDTO), account) ;
    }

    //getAccount

    @Test
    public void getAccountsByEmail() throws Exception {
        given(accountsRepository.getAccount("EMAIL")).willReturn(Optional.of(new Account()));
        assertNotNull(service.getAccount("EMAIL"));
    }

}