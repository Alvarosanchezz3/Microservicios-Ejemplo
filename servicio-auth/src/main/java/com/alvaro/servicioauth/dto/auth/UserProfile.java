package com.alvaro.servicioauth.dto.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class UserProfile implements Serializable {

    private Long id;
    private String name;
    private String username;
    private String email;
    private boolean enabled;
    private String role;
    private Collection<?> authorities;
}
