package com.alvaro.serviciousuarios.service;

import com.alvaro.serviciousuarios.dto.SaveUser;
import com.alvaro.serviciousuarios.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Page<Usuario> findAll(Pageable pageable);

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUsername(String username);

    Usuario saveUser(SaveUser user);

    Usuario updateUser(Long id, Usuario user);

    void deleteUser(Long id);

}

