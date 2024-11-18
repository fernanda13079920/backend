package com.ERP.ERP.repository;
import com.ERP.ERP.model.Usuarios;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<Usuarios, Long>{

    Optional<Usuarios> findByCorreo(String correo);
}
