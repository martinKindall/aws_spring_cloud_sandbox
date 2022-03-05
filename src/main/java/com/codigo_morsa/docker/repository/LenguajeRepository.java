package com.codigo_morsa.docker.repository;

import com.codigo_morsa.docker.model.Lenguaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

import java.util.Map;

@Repository
public class LenguajeRepository {

    private final DynamoDbAsyncTable<Lenguaje> lenguajeTable;

    @Autowired
    public LenguajeRepository(DynamoDbEnhancedAsyncClient dynamoDbClient) {
        lenguajeTable = dynamoDbClient
                .table("lenguaje", TableSchema.fromBean(Lenguaje.class));
    }

    public Flux<Lenguaje> findAll() {
        return Flux.from(lenguajeTable.scan().items());
    }

    public Mono<Lenguaje> getLenguajeByName(String name) {
        return Mono.fromFuture(lenguajeTable.getItem(getKeyBuild(name)));
    }

    public Mono<Void> saveLenguaje(Lenguaje lenguaje) {
        var request = PutItemEnhancedRequest
                .builder(Lenguaje.class)
                .item(lenguaje)
                .conditionExpression(
                        Expression.builder()
                                .expression("attribute_not_exists(#name)")
                                .expressionNames(Map.of("#name", "name"))
                                .build())
                .build();
        return Mono.fromFuture(lenguajeTable.putItem(request));
    }

    private Key getKeyBuild(String name) {
        return Key.builder().partitionValue(name).build();
    }
}
