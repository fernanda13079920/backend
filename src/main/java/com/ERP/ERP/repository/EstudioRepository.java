package com.ERP.ERP.repository;
import com.ERP.ERP.model.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudioRepository  extends JpaRepository<Estudio, Long> {
}
