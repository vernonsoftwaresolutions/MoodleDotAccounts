package com.moodle.account.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Created by andrewlarsen on 2/11/18.
 */
public class AwsConfig {
    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider(){
        return AWSCognitoIdentityProviderClientBuilder.standard().build();
    }
}
