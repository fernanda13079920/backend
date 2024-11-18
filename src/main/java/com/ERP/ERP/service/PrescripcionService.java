package com.ERP.ERP.service;

import com.ERP.ERP.model.Prescripcion;
import com.ERP.ERP.repository.PrescripcionRepository;
import com.ERP.ERP.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PrescripcionService {

    @Autowired
    private PrescripcionRepository prescripcionRepository;
    @Autowired
    private SesionRepository sesionRepository;

    @Transactional
    public void deletePrescriptionsBySesion_Id(Long sesionId) {
        List<Prescripcion> prescripciones = prescripcionRepository.findBySesion_Id(sesionId);
        if (prescripciones.isEmpty()) {
            throw new RuntimeException("No Prescriptions found for the given Sesion ID");
        }
        prescripcionRepository.deleteBySesion_Id(sesionId);
    }
}