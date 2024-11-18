package com.ERP.ERP.repository;
import com.ERP.ERP.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByHistorial_Paciente_Id(Long pacienteId);
   // List<Sesion> findByFechaCierreIsNull();
}
