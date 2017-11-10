package com.moodle.users.service;

import com.moodle.users.client.MoodleTenantClient;
import com.moodle.users.model.User;
import com.moodle.users.model.UserDTO;
import com.moodle.users.model.moodle.SQSResponse;
import com.moodle.users.repository.UsersRepository;
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
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private MoodleTenantClient client;
    private UsersService service;
    private UserDTO userDTO;
    private User user;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new UsersService(usersRepository,client);
        userDTO = new UserDTO();

    }
    @Test
    public void save() throws Exception {
        given(usersRepository.save(anyObject())).willReturn(user);
        given(client.postMessage(any())).willReturn(new SQSResponse());
        assertEquals(service.save(userDTO),user) ;
    }
    @Test
    public void saveCompanyName() throws Exception {
        given(usersRepository.save(anyObject())).willReturn(user);
        given(client.postMessage(any())).willReturn(new SQSResponse());
        assertEquals(service.save(userDTO),user) ;
    }
}