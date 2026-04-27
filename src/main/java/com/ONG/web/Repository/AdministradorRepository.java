package com.ONG.web.Repository;

import com.ONG.web.Model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador,Integer> {
    Optional<Administrador> findByCorreo(String correo);
}
