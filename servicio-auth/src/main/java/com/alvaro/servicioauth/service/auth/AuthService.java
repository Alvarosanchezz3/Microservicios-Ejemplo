package com.alvaro.servicioauth.service.auth;

import com.alvaro.servicioauth.dto.RegisteredUser;
import com.alvaro.servicioauth.dto.SaveUser;
import com.alvaro.servicioauth.dto.auth.AuthenticationRequest;
import com.alvaro.servicioauth.dto.auth.AuthenticationResponse;
import com.alvaro.servicioauth.entity.Usuario;
import com.alvaro.servicioauth.service.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {
        Usuario usuario = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(usuario.getId());
        userDto.setName(usuario.getName());
        userDto.setUsername(usuario.getUsername());
        userDto.setRole(usuario.getRole().getName());

        UserDetails userDetails = userService.loadUserByUsername(usuario.getUsername());

        String jwt = jwtService.generateToken(userDetails, generateExtraClaims(usuario));
        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("username", usuario.getUsername());
            extraClaims.put("role", usuario.getRole().getName());

        List<GrantedAuthority> authorities = userService.getAuthorities(usuario);

        extraClaims.put("authorities", authorities);

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

        Usuario user = userService.findOneByUsername(authRequest.getUsername());
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        String jwt = jwtService.generateToken(userDetails, generateExtraClaims(user));

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setJwt(jwt);

        return authResponse;
    }

    public boolean validateToken(String jwt) {

        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Usuario findLoggedInUser() {
        Authentication auth = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getPrincipal().toString();

        return userService.findOneByUsername(username);
    }
}