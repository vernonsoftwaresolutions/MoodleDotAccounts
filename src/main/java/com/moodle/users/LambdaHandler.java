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
package com.moodle.users;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private Logger log = LoggerFactory.getLogger(LambdaHandler.class);
    private static final String STAGE_KEY = "STAGE_KEY";
    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {

        if (handler == null) {
            try {

                handler = SpringLambdaContainerHandler.getAwsProxyHandler(AppConfig.class);
            } catch (ContainerInitializationException e) {
                log.error("Cannot initialize spring handler", e);
                return null;
            }
        }
        //if the stage is passed in, we want to strip it from base path
        String stageKey = System.getenv(STAGE_KEY);
        String stage = awsProxyRequest.getStageVariables().get(stageKey);
        if(stage != null){

            handler.stripBasePath(stage);

        }

        return handler.proxy(awsProxyRequest, context);
    }
}
