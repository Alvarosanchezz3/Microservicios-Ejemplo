package com.alvaro.servicioauth.service;

import com.alvaro.servicioauth.client.UserFeignClient;
import com.alvaro.servicioauth.dto.SaveUser;
import com.alvaro.servicioauth.entity.Role;
import com.alvaro.servicioauth.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient feignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = feignClient.findOneByUsername(username).getBody();

        if (usuario == null) {
            log.error("No existe el usuario {}", username);
            throw new UsernameNotFoundException("No existe el usuario " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRole().getName()));

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(),
                true, true, true, authorities);
    }

    public Usuario registerOneCustomer(SaveUser newUser) {
        return feignClient.createOne(newUser).getBody();
    }

    public Usuario findOneByUsername (String username) {
        return feignClient.findOneByUsername(username).getBody();
    }

    public List<GrantedAuthority> getAuthorities (Usuario user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN_AUTHORITIES"));
        } else {
            authorities.add(new SimpleGrantedAuthority("CUSTOMER_AUTHORITIES"));
        }
        return authorities;
    }
}