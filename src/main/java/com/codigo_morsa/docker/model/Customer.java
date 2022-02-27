package com.codigo_morsa.docker.model;

import org.springframework.data.annotation.Id;

public class Customer {

    @Id
    public Long id;

    private final String firstName;
    private final String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
