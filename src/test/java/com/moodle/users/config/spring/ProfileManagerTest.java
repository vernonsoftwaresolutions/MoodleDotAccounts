package com.moodle.users.config.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by andrewlarsen on 11/5/17.
 */
public class ProfileManagerTest {

    @Mock
    Environment environment;

    private ProfileManager profileManager;
    private final String[] goodProfileArray = {"DEV"};
    private final String[] tooFewProfileArray = {};
    private final String[] tooManyProfileArray = {"DEV", "Prod"};

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        profileManager = new ProfileManager(environment);
    }
    @Test
    public void getActiveProfile() throws Exception {
        given(environment.getActiveProfiles()).willReturn(goodProfileArray);

        assertEquals(profileManager.getActiveProfile(), goodProfileArray[0]);
    }
    @Test(expected = IllegalArgumentException.class)
    public void getActiveProfileTooFew() throws Exception {
        given(environment.getActiveProfiles()).willReturn(tooFewProfileArray);
        profileManager.getActiveProfile();
    }
    @Test(expected = IllegalArgumentException.class)
    public void getActiveProfileTooMany() throws Exception {
        given(environment.getActiveProfiles()).willReturn(tooManyProfileArray);
        profileManager.getActiveProfile();

    }
}