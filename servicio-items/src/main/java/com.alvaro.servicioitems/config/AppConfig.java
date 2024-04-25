package com.alvaro.servicioitems.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean("clienteRest")
    @LoadBalanced
    public RestTemplate registrarRestTemplate () {
        return new RestTemplate();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer () {
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(10) // 10 llamadas de prueba para entrar en cortocircuito
                        .failureRateThreshold(50) // % de fallos en la prueba para entrar en cortocircuito
                        .waitDurationInOpenState(Duration.ofSeconds(10L)) // 10s para salir del cortocircuito
                        .permittedNumberOfCallsInHalfOpenState(5) // Estado semi-abierto de 5 llamadas
                        .slowCallDurationThreshold(Duration.ofSeconds(2L)) // A partir de 2s es una llamada lenta
                        .slowCallRateThreshold(50) // % de llamadas lentas es mayor del 50% entra en cortocircuito
                        .build())
                    // Si una llamada tarda más de 3s en ejecutarse se da como fallo
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3L)).build())
                    .build();
        });
    }
}