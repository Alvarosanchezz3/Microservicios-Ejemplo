package com.alvaro.gatewayserver.Config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
            http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange((authorize) -> authorize
                .pathMatchers("/api/auth/**").permitAll()
                .pathMatchers("/api/users/**").hasAnyAuthority("ADMIN_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/api/products/**").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/api/items/**").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")
                .pathMatchers("/api/products/**").hasAnyAuthority("ADMIN_AUTHORITIES")
                .pathMatchers("/api/items/**").hasAnyAuthority("ADMIN_AUTHORITIES")
                .anyExchange().authenticated()
		);
        return http.build();
    }
}