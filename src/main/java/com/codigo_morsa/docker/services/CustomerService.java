package com.codigo_morsa.docker.services;

import com.codigo_morsa.docker.model.Customer;
import com.codigo_morsa.docker.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Flux<Customer> getCustomers() {
        return repository.findAll();
    }

    public Mono<Long> saveCustomer(Customer customer) {
        return repository.save(customer).map(cust -> cust.id);
    }
}
