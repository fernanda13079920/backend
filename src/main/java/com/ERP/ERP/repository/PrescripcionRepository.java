package com.ERP.ERP.repository;
import com.ERP.ERP.model.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescripcionRepository extends JpaRepository<Prescripcion, Long> {
/*
    List<Prescripcion> findBySesionId(Long sesionId);

    @Query("DELETE FROM Prescripcion p WHERE p.sesion.id = :sesionId")
    void deleteBySesionId(@Param("sesionId") Long sesionId);
*/
    /*
    List<Prescripcion> findBySesion_Id(Long sesionId);
    @Query("DELETE FROM Prescripcion p WHERE p.sesion.id = 1")
    void deleteBySesionId();

    */

    List<Prescripcion> findBySesion_Id(Long sesionId);
    void deleteBySesion_Id(Long sesionId);

}

