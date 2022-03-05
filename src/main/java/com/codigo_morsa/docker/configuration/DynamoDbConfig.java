package com.codigo_morsa.docker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Value("${dynamoUrl}")
    private String dynamoDbUrl;

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    private DynamoDbAsyncClient getDynamoDbClient() {
        return DynamoDbAsyncClient
                .builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(dynamoDbUrl))
                .build();
    }
}
