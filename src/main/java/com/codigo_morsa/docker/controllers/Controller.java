package com.codigo_morsa.docker.controllers;

import com.codigo_morsa.docker.model.Customer;
import com.codigo_morsa.docker.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class Controller {
    @Value("${message}")
    private String message;

    private final CustomerService customerService;

    @Autowired
    public Controller(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public Flux<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/customer")
    public Mono<Map<String, String>> saveCustomer(
            @RequestBody Customer customer
    ) {
        return customerService
                .saveCustomer(customer)
                .map(id -> Map.of("message", "Customer created with id: " + id));
    }

    @GetMapping("/config")
    public Mono<Map<String, String>> getConfigMessage() {
        return Mono.just(Map.of("message", "Here comes a message from AWS Param Store: " + message));
    }
}
