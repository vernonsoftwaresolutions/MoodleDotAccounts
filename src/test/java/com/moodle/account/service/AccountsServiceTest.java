package com.moodle.account.service;

import com.moodle.account.aws.CognitoClient;
import com.moodle.account.model.Account;
import com.moodle.account.model.AccountDTO;
import com.moodle.account.model.moodle.MoodleSiteRequest;
import com.moodle.account.repository.AccountsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;
    @Mock
    private CognitoClient client;

    private AccountsService service;
    private AccountDTO accountDTO;
    private Account account;
    private MoodleSiteRequest request;
    private String code = "CODE";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new AccountsService(accountsRepository, client);
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
    //getAccountbyCode
    @Test
    public void getAccountsByCodeNull() throws Exception {
        given(client.getUser(code)).willReturn(null);

        assertFalse(service.getAccountByCode(code).isPresent());
    }
    @Test
    public void getAccountsByCode() throws Exception {
        given(client.getUser(code)).willReturn(accountDTO);

        assertEquals(service.getAccountByCode(code).get().getAddress(), accountDTO.getAddress());
        assertEquals(service.getAccountByCode(code).get().getCompanyName(), accountDTO.getCompanyName());
        assertEquals(service.getAccountByCode(code).get().getEmail(), accountDTO.getEmail());
        assertEquals(service.getAccountByCode(code).get().getName(), accountDTO.getName());
        assertEquals(service.getAccountByCode(code).get().getPhoneNumber(), accountDTO.getPhoneNumber());
        assertEquals(service.getAccountByCode(code).get().getUserName(), accountDTO.getUserName());


    }
}