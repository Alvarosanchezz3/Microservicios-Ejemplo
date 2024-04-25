package com.alvaro.serviciousuarios.service;

import com.alvaro.serviciousuarios.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    public List<Role> findAll();

    public Optional<Role> findByName(String name);

}

