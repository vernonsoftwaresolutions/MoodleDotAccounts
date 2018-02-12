package com.moodle.account.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andrewlarsen on 2/11/18.
 */
@Configuration
public class AwsConfig {
    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider(){
        return AWSCognitoIdentityProviderClientBuilder.standard().build();
    }
}
