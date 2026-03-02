package br.com.patrimonium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret}")
    public String secret;

    @Value("${security.jwt.expiration}")
    public Long expiration;
}