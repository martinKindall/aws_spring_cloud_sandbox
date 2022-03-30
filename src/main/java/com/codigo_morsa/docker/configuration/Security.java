package com.codigo_morsa.docker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.OPTIONS;

@EnableWebFluxSecurity
public class Security {
    @Value("${spring.security.oauth2.client.provider.cognito.issuerUri}")
    private String issuer;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers(OPTIONS, "/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .cors()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtDecoder(jwtDecoder());

        return http.build();
    }

    ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromOidcIssuerLocation(issuer);
    }
}
