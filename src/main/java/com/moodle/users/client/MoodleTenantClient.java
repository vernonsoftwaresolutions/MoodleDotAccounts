package com.moodle.users.client;

import com.moodle.users.model.moodle.MoodleTenantRequest;
import com.moodle.users.model.moodle.SQSResponse;

/**
 * Created by andrewlarsen on 11/9/17.
 */
public interface MoodleTenantClient {

    /**
     * Method to post Moodle Tenant request to service to be
     * place on the appropriate queue
     * @param request
     * @return
     */
    SQSResponse postMessage(MoodleTenantRequest request);
}
