package com.codigo_morsa.docker.controllers;

import com.codigo_morsa.docker.model.Customer;
import com.codigo_morsa.docker.services.CustomerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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
    private final Counter customerViewCounter;

    @Value("${message}")
    private String message;

    private final CustomerService customerService;

    @Autowired
    public Controller(CustomerService customerService, MeterRegistry meterRegistry) {
        this.customerService = customerService;
        customerViewCounter = meterRegistry.counter("CUSTOMER.reads");
    }

    @GetMapping("/customer")
    public Flux<Customer> getCustomers() {
        customerViewCounter.increment();
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

    @GetMapping("/launchException")
    public Mono<Map<String, String>> triggerException() {
        throw new RuntimeException("Oops, something unexpected happened.");
    }

    @GetMapping("/launchException2")
    public Mono<Map<String, String>> triggerException2() {
        throw new ArithmeticException("Cannot divide by 0");
    }
}
