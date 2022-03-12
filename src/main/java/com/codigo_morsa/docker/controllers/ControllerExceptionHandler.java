package com.codigo_morsa.docker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ConditionalCheckFailedException.class)
    public @ResponseBody
    HttpErrorInfo handleNotFoundExceptions(ServerHttpRequest request, Exception ex) {
        logger.info("ConditionalCheckFailedException: {}", ex.getMessage());
        return new HttpErrorInfo(CONFLICT, request.getPath().pathWithinApplication().value(), "Lenguaje ya existe!");
    }

    // default handler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    HttpErrorInfo handleUnknownExceptions(ServerHttpRequest request, Exception ex) {
        logger.error("Unknown Exception: {}", ex.getMessage());
        return new HttpErrorInfo(INTERNAL_SERVER_ERROR, request.getPath().pathWithinApplication().value(), "Server error");
    }
}
