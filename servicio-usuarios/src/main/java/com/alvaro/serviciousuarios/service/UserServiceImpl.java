package com.alvaro.serviciousuarios.service;

import com.alvaro.serviciousuarios.dto.SaveUser;
import com.alvaro.serviciousuarios.entity.Role;
import com.alvaro.serviciousuarios.entity.Usuario;
import com.alvaro.serviciousuarios.repository.RoleRepository;
import com.alvaro.serviciousuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public Usuario saveUser(SaveUser saveUser) {
        Usuario user = new Usuario();
        user.setName(saveUser.getName());
        user.setUsername(saveUser.getUsername());
        user.setPassword(passwordEncoder.encode(saveUser.getPassword()));
        user.setEmail(saveUser.getEmail());
        user.setEnabled(true);

        // Crear un nuevo rol "Customer"
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER");

        user.setRole(customerRole);

        return userRepository.save(user);
    }

    @Override
    public Usuario updateUser(Long userId, Usuario user) {
        return userRepository.findById(userId).map(userToUpdate -> {
            if (user.getName() != null) userToUpdate.setName(user.getName());
            if (user.getLastName() != null) userToUpdate.setLastName(user.getLastName());
            if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());

            return userRepository.save(userToUpdate);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}