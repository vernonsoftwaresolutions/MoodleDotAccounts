package com.moodle.users;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.ExceptionHandler;
import com.amazonaws.serverless.proxy.internal.RequestReader;
import com.amazonaws.serverless.proxy.internal.ResponseWriter;
import com.amazonaws.serverless.proxy.internal.SecurityContextWriter;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.moodle.users.lambda.EnvironmentHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public class LambdaHandlerTest {
    @Mock
    RequestReader requestReader;
    @Mock
    ResponseWriter responseWriter;
    @Mock
    SecurityContextWriter securityContextWriter;
    @Mock
    ExceptionHandler exceptionHandler;
    @Mock
    ConfigurableWebApplicationContext applicationContext;
    @Mock
    private AwsProxyRequest awsProxyRequest;
    @Mock
    private Context context;
    @Mock
    private EnvironmentHelper helper;

    @Spy
    private SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler
            = new SpringLambdaContainerHandler<>(requestReader, responseWriter, securityContextWriter, exceptionHandler, applicationContext);

    private LambdaHandler lambdaHandler;

    private String stage = "DEV";

    private AwsProxyResponse response;
    public LambdaHandlerTest() throws ContainerInitializationException {
        response = new AwsProxyResponse();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        lambdaHandler = new LambdaHandler(handler, helper);
    }
    @Test
    public void handleRequest() throws Exception {
        given(helper.getStageName(awsProxyRequest)).willReturn(Optional.of(stage));
        Mockito.doReturn(response).when(handler).proxy(awsProxyRequest, context);

        AwsProxyResponse result = this.lambdaHandler.handleRequest(awsProxyRequest, context);

        assertEquals(result, response);

        //make sure the spy was called
        Mockito.verify(handler).proxy(awsProxyRequest, context);

    }
    @Test
    public void handleRequestNoBasePath() throws Exception {
        given(helper.getStageName(awsProxyRequest)).willReturn(Optional.empty());
        Mockito.doReturn(response).when(handler).proxy(awsProxyRequest, context);

        AwsProxyResponse result = this.lambdaHandler.handleRequest(awsProxyRequest, context);

        assertEquals(result, response);

        //make sure the spy was called
        Mockito.verify(handler).proxy(awsProxyRequest, context);
        Mockito.verifyNoMoreInteractions(handler);

    }
}