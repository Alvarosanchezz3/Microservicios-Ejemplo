package com.alvaro.servicioauth.config.security;

import brave.Tracer;
import com.alvaro.servicioauth.client.UserFeignClient;
import com.alvaro.servicioauth.entity.Usuario;
import com.alvaro.servicioauth.service.UserService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SpringSecurityConfig {

    private final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private UserFeignClient feignClient;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {

        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder( passwordEncoder() );
        authenticationStrategy.setUserDetailsService( userDetailsService() );

        return authenticationStrategy;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return (username) -> {
            try {
                Usuario usuario = feignClient.findOneByUsername(username).getBody();

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(usuario.getRole().getName()));

                return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(),
                        true, true, true, authorities);

                /* Capturamos si hay una excepción al hacer la llamada para conseguir el usuario y la añadimos al log,
                 * a la traza añadiendola como tag para verlo en Zipkin y utilizamos una excepción */
            } catch (FeignException e) {
                String error = "No existe el usuario " + username;
                log.error(error);
                tracer.currentSpan().tag("error.message", error);
                throw new UsernameNotFoundException("No existe el usuario " + username);
            }
        };
    }
}