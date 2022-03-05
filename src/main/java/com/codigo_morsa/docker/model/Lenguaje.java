package com.codigo_morsa.docker.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Lenguaje {

    private String name;
    private long publishedAt;
    private boolean tipadoEstatico;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("published_at")
    public long getPublishedAt() {
        return publishedAt;
    }

    @DynamoDbAttribute("tipado_estatico")
    public boolean getTipadoEstatico() {
        return tipadoEstatico;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setTipadoEstatico(boolean tipadoEstatico) {
        this.tipadoEstatico = tipadoEstatico;
    }
}
