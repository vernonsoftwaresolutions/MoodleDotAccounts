package com.moodle.account.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by andrewlarsen on 2/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AwsConfig.class})
public class AwsConfigTest {
    @Autowired
    private AWSCognitoIdentityProvider provider;
    @Test
    public void awsCognitoIdentityProvider() throws Exception {
        assertNotNull(provider);
    }

}