package com.codigo_morsa.docker.controllers;

import com.codigo_morsa.docker.model.Lenguaje;
import com.codigo_morsa.docker.repository.LenguajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class LenguajeController {

    private final LenguajeRepository repository;

    @Autowired
    public LenguajeController(LenguajeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/lenguaje")
    public Flux<Lenguaje> getAllLenguajes() {
        return repository.findAll();
    }

    @GetMapping("/lenguaje/{name}")
    public Mono<Lenguaje> getAllLenguajes(@PathVariable String name) {
        return repository.getLenguajeByName(name);
    }

    @PostMapping("/lenguaje")
    public Mono<Map<String, String>> saveLenguaje(@RequestBody Lenguaje lenguaje) {
        return repository.saveLenguaje(lenguaje).thenReturn(Map.of("message", "Lenguaje creado!"));
    }
}
