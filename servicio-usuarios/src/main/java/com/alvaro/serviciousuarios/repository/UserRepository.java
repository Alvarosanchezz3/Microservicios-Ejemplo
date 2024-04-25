package com.alvaro.serviciousuarios.repository;

import com.alvaro.serviciousuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PagingAndSortingRepository hereda de CrudRepository pero nos sirve para paginar
@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername (String username);

}
