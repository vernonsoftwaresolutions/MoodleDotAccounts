package com.moodle.users.lambda;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by andrewlarsen on 11/4/17.
 *
 * Cop-out name.  todo-think of better name once use case for class
 * is fully fleshed out
 */
@Component
public class EnvironmentHelper {
    //Environment Variable containing the Key to look up the stage
    private static final String STAGE_KEY = "STAGE_KEY";
    private Logger log = LoggerFactory.getLogger(EnvironmentHelper.class);


    /**
     * Method to interrogate the awsProxyRequest to retrieve stage name
     * @param awsProxyRequest
     * @return
     */
    public Optional<String> getStageName(AwsProxyRequest awsProxyRequest) {
        //make sure awsProxy exists
        if(awsProxyRequest == null){
            log.warn("AWSProxy is null, it should not be!");
            return Optional.empty();
        }
        //check if stage key exists
        if(System.getenv().containsKey(STAGE_KEY)){
            //if so great, grab stageKey
            String stageKey = System.getenv(STAGE_KEY);
            //lookup variable and return
            return Optional.of(awsProxyRequest.getStageVariables().get(stageKey));

        }
        else {
            //otherwise return
            return Optional.empty();

        }
    }
}
