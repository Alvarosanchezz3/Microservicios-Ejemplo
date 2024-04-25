package com.alvaro.servicioauth.Controller;


import com.alvaro.servicioauth.dto.auth.AuthenticationRequest;
import com.alvaro.servicioauth.dto.auth.AuthenticationResponse;
import com.alvaro.servicioauth.dto.auth.UserProfile;
import com.alvaro.servicioauth.entity.Usuario;
import com.alvaro.servicioauth.service.UserService;
import com.alvaro.servicioauth.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("validate-token")
    public ResponseEntity<Boolean> validate (@RequestParam String jwt) {
        boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody @Valid AuthenticationRequest authRequest) {

        AuthenticationResponse authResponse = authService.login(authRequest);

        return ResponseEntity.ok(authResponse);
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN_AUTHORITIES', 'CUSTOMER_AUTHORITIES')")
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> findMyProfile () {
        Usuario user = authService.findLoggedInUser();

        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId());
        userProfile.setName(user.getName());
        userProfile.setUsername(user.getUsername());
        userProfile.setEmail(user.getEmail());
        userProfile.setEnabled(user.isEnabled());
        userProfile.setRole(user.getRole().getName());

        List<GrantedAuthority> authorities = userService.getAuthorities(user);
        userProfile.setAuthorities(authorities);

        return ResponseEntity.ok(userProfile);
    }
}