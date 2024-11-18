package com.ERP.ERP.repository;

import com.ERP.ERP.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    // Busca un usuario por su correo
    Optional<Usuarios> findByCorreo(String correo);
}