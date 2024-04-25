package com.alvaro.serviciousuarios.controller;

import com.alvaro.serviciousuarios.dto.SaveUser;
import com.alvaro.serviciousuarios.entity.Usuario;
import com.alvaro.serviciousuarios.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listUsers")
    public ResponseEntity<Page<Usuario>> findAll(Pageable pageable) {

        Page<Usuario> usersPage = userService.findAll(pageable);

        if (usersPage.hasContent()) {
            return ResponseEntity.ok(usersPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Usuario> findOneById(@PathVariable Long userId) {

        Optional<Usuario> product = userService.findById(userId);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("user/search-by-username")
    public ResponseEntity<Usuario> findOneByUsername(@RequestParam String username) {

        Optional<Usuario> product = userService.findByUsername(username);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createOne(@RequestBody @Valid SaveUser saveUser) {
        Usuario user = userService.saveUser(saveUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateOne(@PathVariable Long userId, @RequestBody Usuario user) {
        Usuario updatedUser = userService.updateUser(userId, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario con Id " + userId + " no existe.");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUserById(@PathVariable Long userId) {
        Optional<Usuario> userFromDb = userService.findById(userId);

        if (userFromDb.isPresent()) {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("El usuario con Id " + userId + " fue borrado con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario con Id " + userId + " no existe.");
        }
    }
}
