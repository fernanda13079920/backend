package com.ERP.ERP.repository;
import com.ERP.ERP.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface HistorialRepository extends JpaRepository<Historial, Long> {
    Optional<Historial> findByPaciente_Id(Long id_paciente);
}