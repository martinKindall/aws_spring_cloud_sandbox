package com.codigo_morsa.docker.repository;

import com.codigo_morsa.docker.model.Lenguaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class LenguajeRepository {

    private final DynamoDbAsyncTable<Lenguaje> lenguajeTable;

    @Autowired
    public LenguajeRepository(DynamoDbEnhancedAsyncClient dynamoDbClient) {
        lenguajeTable = dynamoDbClient
                .table("lenguaje", TableSchema.fromBean(Lenguaje.class));
    }

    public Mono<Lenguaje> getLenguajeByName(String name) {
        return Mono.fromFuture(lenguajeTable.getItem(getKeyBuild(name)));
    }

    private Key getKeyBuild(String name) {
        return Key.builder().partitionValue(name).build();
    }
}
