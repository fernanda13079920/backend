package com.ERP.ERP.repository;

import com.ERP.ERP.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
