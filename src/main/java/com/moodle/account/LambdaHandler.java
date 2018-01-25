/*
 * Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.moodle.account;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.moodle.account.config.AppConfig;
import com.moodle.account.lambda.EnvironmentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private Logger log = LoggerFactory.getLogger(LambdaHandler.class);
    private EnvironmentHelper helper;

    public LambdaHandler() {
        log.info("Logging from no args constructor");
        //todo- Think through is there if there is a better way to pass this around
        //it's not horrible but since it's also a spring bean we'll have two of these guys hanging around
        //so it's worth thinking through a better pattern perhaps
        helper = new EnvironmentHelper();
    }

    //todo-I hate that I have to create a dummy constructor for this
    //todo- I need to fully understand how this framework works so I can start to spin up
    // todo- full contexts within Junit but that's not really MVP
    public LambdaHandler(SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler,
                         EnvironmentHelper helper) {
        log.info("Logging from args constructor");
        this.handler = handler;
        this.helper = helper;
    }

    /**
     * Method to hijack the aws lambda request and create spring context
     * To load controllers to process request
     * Further info https://github.com/awslabs/aws-serverless-java-container
     * @param awsProxyRequest
     * @param context
     * @return
     */
    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {

        if (this.handler == null) {
            log.debug("Handler is null, creating new instance");
            try {

                this.handler = SpringLambdaContainerHandler.getAwsProxyHandler(AppConfig.class);
                log.debug("Created handler");
            } catch (ContainerInitializationException e) {
                log.error("Cannot initialize spring handler", e);
                return null;
            }
        }
        return this.handler.proxy(awsProxyRequest, context);
    }


}
