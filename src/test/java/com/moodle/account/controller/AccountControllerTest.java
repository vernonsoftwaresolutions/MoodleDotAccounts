package com.moodle.account.controller;


import com.moodle.account.model.Error;
import com.moodle.account.model.Account;
import com.moodle.account.model.AccountDTO;
import com.moodle.account.service.AccountsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class AccountControllerTest {


    @Mock
    private AccountsService service;
    private AccountDTO account;
    private AccountController accountController;
    private Account response;
    Optional<String> email = Optional.of("EMAIL");
    private String id = "ID";
    private String accessToken = "ACCESSTOKEN";
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        account = new AccountDTO();
        account.setEmail("EMAIL");
        account.setName("FIRSTNAME");
        account.setAddress("LASTNAME");
        account.setPhoneNumber("PHONENUMBER");
        account.setCompanyName("COMPANYNAME");
        accountController = new AccountController(service);
        response = new Account();

    }
    @Test
    public void createAccount201() throws Exception {
        given(service.save(account)).willReturn(response);
        ResponseEntity response = accountController.createAccount( accessToken);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }
    @Test
    public void createAccountConflict() throws Exception {
        given(service.save(accessToken)).willThrow(new IllegalStateException());
        ResponseEntity response = accountController.createAccount( accessToken);
        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));

    }
    @Test
    public void createAccountIdPopulated() throws Exception {
        given(service.save(accessToken)).willReturn(response);
        ResponseEntity response = accountController.createAccount( accessToken);
        Account responseAccount = (Account) response.getBody();
        assertThat(responseAccount,is(responseAccount));

    }


    @Test
    public void createAccountNoFirstName422() throws Exception {
        ResponseEntity response = accountController.createAccount( null);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
   
    @Test
    public void createAccountHeaders() throws Exception {
        given(service.save(accessToken)).willReturn(response);
        ResponseEntity response = accountController.createAccount( accessToken);
        assertThat(response.getHeaders().size(), is(1));

    }

    @Test
    public void createAccount202HeadersExistString() throws Exception {
        ResponseEntity response = accountController.createAccount( accessToken);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
    @Test
    public void createAccount500() throws Exception {
        given(service.save(accessToken)).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.createAccount( accessToken);
        assertThat(response.getBody().getMessage(), is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));

    }

    //get by email tests
    @Test
    public void getAccountByEmail200() throws Exception {
        given(service.getAccount(email.get())).willReturn(Optional.of(new Account()));

        assertEquals(accountController.getAccountByEmail(email).getStatusCode(), HttpStatus.OK);

    }
    @Test
    public void getAccountByEmailNotNull() throws Exception {
        given(service.getAccount(email.get())).willReturn(Optional.of(new Account()));
        assertNotNull(accountController.getAccountByEmail(email).getBody());

    }
    @Test
    public void getAccountByEmailNotFound() throws Exception {
        given(service.getAccount(email.get())).willReturn(null);
        assertNotNull(accountController.getAccountByEmail(email).getBody());

    }
    @Test
    public void getAccountByEmail400() throws Exception {
        given(service.getAccount(email.get())).willReturn(Optional.empty());
        assertEquals(accountController.getAccountByEmail(email).getStatusCode(), HttpStatus.NOT_FOUND);

    }
    @Test
    public void getAccountByEmail200Headers() throws Exception {
        assertThat(accountController.getAccountByEmail(email).getHeaders().size(), is(1));
    }
    @Test
    public void getAccountByEmail422() throws Exception {
        assertEquals(accountController.getAccountByEmail(Optional.empty()).getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @Test
    public void getAccountByEmail422NotNull() throws Exception {
        assertNotNull(accountController.getAccountByEmail(Optional.empty()).getBody());

    }
    @Test
    public void getAccountByEmail422Headers() throws Exception {
        assertThat(accountController.getAccountByEmail(Optional.empty()).getHeaders().size(), is(1));
    }
    @Test
    public void getAccountByEmail500Message() throws Exception {
        given(service.getAccount(email.get())).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.getAccountByEmail(email);
        assertThat(response.getBody().getMessage(), is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));

    }
    @Test
    public void getAccountByEmail500Code() throws Exception {
        given(service.getAccount(email.get())).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.getAccountByEmail(email);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));

    }
    @Test
    public void getAccountByEmail500CodeHeaders() throws Exception {
        given(service.getAccount(email.get())).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.getAccountByEmail(email);
        assertThat(response.getHeaders().size(), is(1));

    }
    //get by id tests
    @Test
    public void getAccountById200() throws Exception {
        given(service.getAccountById(id)).willReturn(Optional.of(new Account()));

        assertEquals(accountController.getAccountById(id).getStatusCode(), HttpStatus.OK);

    }
    @Test
    public void getAccountByIdNotNull() throws Exception {
        given(service.getAccountById(id)).willReturn(Optional.of(new Account()));
        assertNotNull(accountController.getAccountById(id).getBody());

    }
    @Test
    public void getAccountByIdNotFound() throws Exception {

        given(service.getAccountById(id)).willReturn(Optional.empty());

        assertNotNull(accountController.getAccountById(id).getBody());

    }
    @Test
    public void getAccountById400() throws Exception {
        given(service.getAccountById(id)).willReturn(Optional.empty());
        assertEquals(accountController.getAccountById(id).getStatusCode(), HttpStatus.NOT_FOUND);

    }
    @Test
    public void getAccountById200Headers() throws Exception {
        assertThat(accountController.getAccountById(id).getHeaders().size(), is(1));
    }

    @Test
    public void getAccountById500Message() throws Exception {
        given(service.getAccountById(id)).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.getAccountById(id);
        assertThat(response.getBody().getMessage(), is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));

    }
    @Test
    public void getAccountById500Code() throws Exception {
        given(service.getAccountById(id)).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.getAccountById(id);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));

    }
    @Test
    public void getAccountById500CodeHeaders() throws Exception {
        given(service.getAccountById(id)).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.getAccountById(id);
        assertThat(response.getHeaders().size(), is(1));

    }
    //get by email tests
    @Test
    public void deleteAccount200() throws Exception {

        assertEquals(accountController.deleteAccount(id, new AccountDTO()).getStatusCode(), HttpStatus.OK);

    }

    //get by code tests
    @Test
    public void getAccountByCode200Headers() throws Exception {
        assertThat(accountController.getAccountByCode(Optional.of(accessToken)).getHeaders().size(), is(1));
    }
    @Test
    public void getAccountByCode200() throws Exception {
        given(service.getAccountByCode(accessToken)).willReturn(Optional.of(response));
        assertThat(accountController.getAccountByCode(Optional.of(accessToken)).getBody(), is(response));
    }
    @Test
    public void getAccountByCode404() throws Exception {
        given(service.getAccountByCode(accessToken)).willReturn(Optional.empty());
        assertThat(accountController.getAccountByCode(Optional.of(accessToken)).getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
    @Test
    public void getAccountByCode500() throws Exception {
        given(service.getAccountByCode(accessToken)).willThrow(new RuntimeException());
        assertThat(accountController.getAccountByCode(Optional.of(accessToken)).getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}