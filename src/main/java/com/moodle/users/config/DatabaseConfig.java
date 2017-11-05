package com.moodle.users.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
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
    Environment environment;

    private static final String PROFILE_KEY = "profile";
    private static final String TABLE_KEY = "TABLE_NAME";

    @Value("${aws.dynamodb.tablename}")
    private String tableNameString;
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
        log.debug("About to build config");

        Map<String, String> env = System.getenv();

        log.debug("Retrieved environment variables {}", env);

        String table = env.getOrDefault(TABLE_KEY, TABLE_KEY);
        log.debug("Retrieved table name ", table);
        String stage = env.getOrDefault(PROFILE_KEY, PROFILE_KEY);
        log.debug("Retrieved stage name ", stage);
        String tableName = table + "_" + stage;
        log.debug("Created tablename {}", tableName);

        log.debug("TableNameString {} ", tableNameString);
        //todo- sorry for debuging REMOVE ME!
        for (final String profileName : environment.getActiveProfiles()) {
            log.info("Currently active profile - " + profileName);
        }

        return DynamoDBMapperConfig
                .builder()
                .withTableNameResolver((clazz, config) -> {
                    return tableNameString;
                })
                .build();
    }
}
