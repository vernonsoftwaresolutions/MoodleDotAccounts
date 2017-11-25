package com.moodle.account.client;

import com.moodle.account.model.moodle.MoodleSiteRequest;
import com.moodle.account.model.moodle.SQSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * Created by andrewlarsen on 11/9/17.
 */
@Component
public class MoodleTenantClientImpl implements MoodleTenantClient {
    private Logger log = LoggerFactory.getLogger(MoodleTenantClientImpl.class);

    private static final String URL_KEY = "POST_TENANT_URL";
    private RestTemplate restTemplate;

    private String url;

    @Autowired
    public MoodleTenantClientImpl(RestTemplate restTemplate,
                                  @Value("${post.tenant.url}") String url) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public SQSResponse postMessage(MoodleSiteRequest request) {
        SQSResponse response = restTemplate.postForEntity(url,
                request, SQSResponse.class).getBody();
        return response;
    }
}
