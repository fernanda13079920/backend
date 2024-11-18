package com.ERP.ERP.repository;
import com.ERP.ERP.model.SesionEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionEstudioRepository extends JpaRepository<SesionEstudio, Long> {
    List<SesionEstudio> findBySesion_Id(Long sesionId);
}
