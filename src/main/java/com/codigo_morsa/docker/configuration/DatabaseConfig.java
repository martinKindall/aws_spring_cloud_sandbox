package com.codigo_morsa.docker.configuration;

import com.codigo_morsa.docker.model.Customer;
import com.codigo_morsa.docker.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DatabaseConfig {

    @Bean
    public CommandLineRunner demo (CustomerRepository repository) {
        return (args) -> {
            repository.saveAll(Arrays.asList(
                    new Customer("Codigo", "Morsa"),
                    new Customer("Ada", "Lovelace"),
                    new Customer("Steve", "Woz")
            )).then().block();
        };
    }
}
