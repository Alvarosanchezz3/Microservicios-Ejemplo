package com.alvaro.serviciousuarios.controller;

import com.alvaro.serviciousuarios.entity.Role;
import com.alvaro.serviciousuarios.entity.Usuario;
import com.alvaro.serviciousuarios.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/listRoles")
    public List<Role> listAll () {
        return roleService.findAll();
    }

    @GetMapping("role/search-by-name")
    public ResponseEntity<Role> findOneByName(@RequestParam String name) {

        Optional<Role> role = roleService.findByName(name);

        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
