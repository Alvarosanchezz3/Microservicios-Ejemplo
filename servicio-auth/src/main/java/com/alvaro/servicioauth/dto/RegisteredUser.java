package com.alvaro.servicioauth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisteredUser implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String email;

    private String role;

    private String jwt;
}
