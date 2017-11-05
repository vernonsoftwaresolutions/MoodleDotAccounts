package com.moodle.users.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.moodle.users.config.spring.ProfileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by andrewlarsen on 11/4/17.
 */
@Component
public class DatabaseConfig {
    private Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private ProfileManager profileManager;

    @Autowired
    private Environment environment;

    private static final String PROFILE_KEY = "profile";
    private static final String TABLE_KEY = "TABLE_NAME";

    /**
     * Bean to retrieve DynamoDBMapper
     * @return
     */
    @Bean
    public DynamoDBMapper getMapper() {
        log.debug("About to build mapper bean");
        return new DynamoDBMapper(buildClient(), buildConfig());
    }

    /**
     * Method to Build DynamoDB client
     * @return
     */
    private AmazonDynamoDB buildClient() {
        log.debug("Creating AmazonDynamoDB client with standard config");
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    /**
     * Method to create DynamoDBMapper with dynamic table name
     * @return
     */
    private DynamoDBMapperConfig buildConfig() {
        //note-the following logic is a little too tightly
        //couple to the active profile.  However,
        //i am trying to avoid too much boilerplate
        //for now.  Can refactor later to include more
        //sophisticated environment specific properties
        log.debug("About to build config");

        Map<String, String> env = System.getenv();

        log.debug("Retrieved environment variables {}", env);

        String table = env.getOrDefault(TABLE_KEY, TABLE_KEY);
        log.debug("Retrieved table name ", table);

        String tableName = table + "_" + profileManager.getActiveProfile();
        log.debug("Created tablename {}", tableName);
        //dynamically set dynamodb table name
        return DynamoDBMapperConfig
                .builder()
                .withTableNameResolver((clazz, config) -> {
                    return tableName;
                })
                .build();
    }
}
