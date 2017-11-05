package com.moodle.users.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by andrewlarsen on 11/4/17.
 */
@Component
public class DatabaseConfig {
    private static final String PROFILE_KEY = "profile";
    private static final String TABLE_KEY = "TABLE_NAME";
    /**
     * Bean to retrieve DynamoDBMapper
     * @return
     */
    @Bean
    public DynamoDBMapper getMapper() {
        return new DynamoDBMapper(buildClient(), buildConfig());
    }

    /**
     * Method to Build DynamoDB client
     * @return
     */
    private AmazonDynamoDB buildClient() {
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

        Map<String, String> env = System.getenv();
        String table = env.getOrDefault(TABLE_KEY, TABLE_KEY);
        String stage = env.getOrDefault(PROFILE_KEY, PROFILE_KEY);
        String tableName = table + "_" + stage;

        return DynamoDBMapperConfig
                .builder()
                .withTableNameResolver((clazz, config) -> {
                    return tableName;
                })
                .build();
    }
}
