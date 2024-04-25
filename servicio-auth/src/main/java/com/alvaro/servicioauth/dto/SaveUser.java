package com.alvaro.servicioauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaveUser implements Serializable {

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotBlank
    private String username;

    @Size(min = 8)
    private String password;

    @NotBlank
    @Email
    private String email;
}
