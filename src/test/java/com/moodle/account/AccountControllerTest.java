package com.moodle.account;


import com.moodle.account.controller.AccountController;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class AccountControllerTest {


    @Mock
    private AccountsService service;
    private AccountDTO user;
    private AccountController accountController;
    private Account response;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        user = new AccountDTO();
        user.setEmail("EMAIL");
        user.setFirstName("FIRSTNAME");
        user.setLastName("LASTNAME");
        user.setPhoneNumber("PHONENUMBER");
        user.setCompanyName("COMPANYNAME");
        accountController = new AccountController(service);
        response = new Account();

    }
    @Test
    public void createUser201() throws Exception {
        given(service.save(user)).willReturn(response);
        ResponseEntity response = accountController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    public void createUserIdPopulated() throws Exception {
        given(service.save(user)).willReturn(response);
        ResponseEntity response = accountController.createUser(this.user);
        Account responseAccount = (Account) response.getBody();
        assertThat(responseAccount,is(responseAccount));

    }

    @Test
    public void createUserNoCompanyName() throws Exception {
        user.setCompanyName(null);
        ResponseEntity response = accountController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoFirstName422() throws Exception {
        user.setFirstName(null);
        ResponseEntity response = accountController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoLastName422() throws Exception {
        user.setLastName(null);
        ResponseEntity response = accountController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoLastNameMessage() throws Exception {
        user.setLastName(null);
        ResponseEntity<Error> response = accountController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserNulluser() throws Exception {
        ResponseEntity<Error> response = accountController.createUser(null);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserNoFirstNameMessage() throws Exception {
        user.setFirstName(null);
        ResponseEntity<Error> response = accountController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserHeadersExist() throws Exception {
        user.setFirstName(null);
        ResponseEntity<Error> response = accountController.createUser(this.user);
        assertThat(response.getHeaders().size(), is(1));

    }
    @Test
    public void createUserHeaders() throws Exception {
        given(service.save(user)).willReturn(response);
        ResponseEntity response = accountController.createUser(this.user);
        assertThat(response.getHeaders().size(), is(1));

    }
    @Test
    public void createUser422HeadersExistString() throws Exception {
        user.setFirstName(null);
        ResponseEntity<Error> response = accountController.createUser(this.user);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
    @Test
    public void createUser202HeadersExistString() throws Exception {
        ResponseEntity response = accountController.createUser(this.user);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
    @Test
    public void createUser500() throws Exception {
        given(service.save(user)).willThrow(new RuntimeException());
        ResponseEntity<Error> response = accountController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));

    }
}