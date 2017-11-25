package com.moodle.account.factory;

import com.moodle.account.model.Account;
import com.moodle.account.model.moodle.MoodleSiteRequest;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by andrewlarsen on 11/10/17.
 */
@Component
public class MoodleSiteRequestFactory {
    private static final String STACK_NAME = "moodle-ecs-single";
    private static final String VPC_ID = "vpc-c7aa77be";
    private static final String HOSTED_ZONE = "vssdevelopment.com";
    /**
     * Helper method to create Moodle Tenant Request
     * @param account
     * @return
     */
    public MoodleSiteRequest createRequest(Account account){
        Random rand = new Random();

        int priority = rand.nextInt(100) + 1;

        MoodleSiteRequest request = new MoodleSiteRequest();
        request.setClientName(account.getCompanyName());
        request.setId(account.getId());
        request.setPriority(priority);

        //todo- fields that need to be removed
        request.setHostedZoneName(HOSTED_ZONE);
        request.setStackName(STACK_NAME);
        request.setVpcId(VPC_ID);

        return request;
    }
}
