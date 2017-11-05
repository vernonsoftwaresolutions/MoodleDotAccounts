package com.moodle.users.config.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by andrewlarsen on 11/5/17.
 */
@Component
public class ProfileManager {

    private Logger log = LoggerFactory.getLogger(ProfileManager.class);

    Environment environment;

    @Autowired
    public ProfileManager(Environment environment) {
        this.environment = environment;
    }


    /**
     * Method to get active profile from environment
     * @return
     * @throws IllegalArgumentException
     */
    public String getActiveProfile() throws IllegalArgumentException{
        String[] profiles = environment.getActiveProfiles();
        if(profiles.length != 1){
            log.error("Illegal number of Spring Profiles found when trying to get active profile");
            throw new IllegalArgumentException("Multiple Profiles Found");
        }

        return profiles[0];

    }
}
