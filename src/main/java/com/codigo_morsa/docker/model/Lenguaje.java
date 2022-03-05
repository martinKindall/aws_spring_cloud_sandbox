package com.codigo_morsa.docker.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Lenguaje {

    public String name;
    public long published_at;
    public boolean tipado_estatico;

    @DynamoDbPartitionKey
    public String getName() {
        return name;
    }
}
