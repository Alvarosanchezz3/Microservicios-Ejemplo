package com.alvaro.serviciousuarios.repository;

import com.alvaro.serviciousuarios.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName (String nombre);
}
