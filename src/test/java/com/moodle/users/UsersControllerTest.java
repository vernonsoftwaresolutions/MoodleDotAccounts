package com.moodle.users;


import com.moodle.users.controller.UsersController;
import com.moodle.users.model.Error;
import com.moodle.users.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class UsersControllerTest {
    private User user;
    @Before
    public void setup(){
        user = new User();
        user.setEmail("EMAIL");
        user.setFirstName("FIRSTNAME");
        user.setLastName("LASTNAME");
        user.setPhoneNumber("PHONENUMBER");
    }
    @Test
    public void createUser201() throws Exception {
        UsersController usersController = new UsersController();
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    public void createUserIdPopulated() throws Exception {
        UsersController usersController = new UsersController();
        ResponseEntity response = usersController.createUser(this.user);
        User responseUser = (User) response.getBody();
        assertThat(responseUser.getId(), notNullValue());

    }

    @Test
    public void createUserNoFirstName422() throws Exception {
        UsersController usersController = new UsersController();
        user.setFirstName(null);
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoLastName422() throws Exception {
        UsersController usersController = new UsersController();
        user.setLastName(null);
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }
    @Test
    public void createUserNoLastNameMessage() throws Exception {
        UsersController usersController = new UsersController();
        user.setLastName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserNoFirstNameMessage() throws Exception {
        UsersController usersController = new UsersController();
        user.setFirstName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getBody().getMessage(), is("Missing Required Fields"));

    }
    @Test
    public void createUserHeadersExist() throws Exception {
        UsersController usersController = new UsersController();
        user.setFirstName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getHeaders().size(), is(1));

    }
    @Test
    public void createUserHeaders() throws Exception {
        UsersController usersController = new UsersController();
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getHeaders().size(), is(1));

    }
    @Test
    public void createUser422HeadersExistString() throws Exception {
        UsersController usersController = new UsersController();
        user.setFirstName(null);
        ResponseEntity<Error> response = usersController.createUser(this.user);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
    @Test
    public void createUser202HeadersExistString() throws Exception {
        UsersController usersController = new UsersController();
        ResponseEntity response = usersController.createUser(this.user);
        assertThat(response.getHeaders().get("Access-Control-Allow-Origin").get(0), is("*"));

    }
}