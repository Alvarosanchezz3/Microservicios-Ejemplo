package com.alvaro.servicioproductos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class Saveproduct implements Serializable {

    @NotBlank
    private String nombre;

    @NotNull
    @Min(1)
    private Double precio;
}
