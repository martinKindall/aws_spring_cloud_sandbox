package com.codigo_morsa.docker.controllers;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ConditionalCheckFailedException.class)
    public @ResponseBody
    HttpErrorInfo handleNotFoundExceptions(ServerHttpRequest request, Exception ex) {

        return new HttpErrorInfo(CONFLICT, request.getPath().pathWithinApplication().value(), "Lenguaje ya existe!");
    }
}
