package com.codigo_morsa.docker.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.Map;

public class ClaimAdapter implements
        Converter<Map<String, Object>, Map<String, Object>> {

    private final MappedJwtClaimSetConverter delegate =
            MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);

        Object adminClaim = convertedClaims.get("custom:admin");
        if (adminClaim != null && adminClaim.equals("true")) {
            convertedClaims.put("scope", "admin");
        }

        return convertedClaims;
    }
}
