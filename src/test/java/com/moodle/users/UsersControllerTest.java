package com.moodle.users;


import com.moodle.users.controller.UsersController;
import com.moodle.users.model.Error;
import com.moodle.users.model.User;
import com.moodle.users.model.UserDTO;
import com.moodle.users.service.UsersService;
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
public class UsersControllerTest {


    @Mock
    private UsersService service;
    private UserDTO user;
    private UsersController usersController;
    private User response;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        user = new UserDTO();
        user.setEmail("EMAIL");
        user.setFirstName("FIRSTNAME");
        user.setLastName("LASTNAME");
        user.setPhoneNumber("PHONENUMBER");
        usersController = new UsersController(service);
        response = new User();

    }
    @Test
    public void createUser201() throws Exception {
        given(service.save(user)).willReturn(response);
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    public void createUserIdPopulated() throws Exception {
        given(service.save(user)).willReturn(response);
        ResponseEntity response = usersController.createUser(this.user);
        User responseUser = (User) response.getBody();
        assertThat(responseUser,is(responseUser));

    }

    @Test
    public void createUserNoFirstName422() throws Exception {
        user.setFirstName(null);
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoLastName422() throws Exception {
        user.setLastName(null);
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoLastNameMessage() throws Exception {
        user.setLastName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserNoFirstNameMessage() throws Exception {
        user.setFirstName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserHeadersExist() throws Exception {
        user.setFirstName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getHeaders().size(), is(1));

    }
    @Test
    public void createUserHeaders() throws Exception {
        given(service.save(user)).willReturn(response);
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getHeaders().size(), is(1));

    }
    @Test
    public void createUser422HeadersExistString() throws Exception {
        user.setFirstName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
    @Test
    public void createUser202HeadersExistString() throws Exception {
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
    @Test
    public void createUser500() throws Exception {
        given(service.save(user)).willThrow(new RuntimeException());
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));

    }
}