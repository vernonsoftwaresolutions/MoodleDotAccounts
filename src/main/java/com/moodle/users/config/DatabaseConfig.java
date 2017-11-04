package com.moodle.users.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by andrewlarsen on 11/4/17.
 */
@Component
public class DatabaseConfig {

    @Value("${aws.dynamodb.tablename}")
    private String tableName;

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
        return DynamoDBMapperConfig
                .builder()
                .withTableNameResolver((clazz, config) -> {
                    return tableName;
                })
                .build();
    }
}
