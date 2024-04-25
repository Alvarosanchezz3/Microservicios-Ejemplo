package com.alvaro.servicioauth.client;

import com.alvaro.servicioauth.dto.SaveUser;
import com.alvaro.servicioauth.entity.Usuario;
import com.alvaro.servicioauth.entity.Role;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "servicio-usuarios")
public interface UserFeignClient {

    @GetMapping("user/search-by-username")
    public ResponseEntity<Usuario> findOneByUsername(@RequestParam String username);

    @PostMapping
    public ResponseEntity<Usuario> createOne(@RequestBody @Valid SaveUser saveUser);

    @GetMapping("role/search-by-name")
    public ResponseEntity<Role> findOneByName(@RequestParam String name);
}
