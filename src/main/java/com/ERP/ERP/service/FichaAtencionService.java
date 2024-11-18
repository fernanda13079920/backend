package com.ERP.ERP.service;

import com.ERP.ERP.model.FichaAtencion;
import com.ERP.ERP.model.Medico;
import com.ERP.ERP.model.Paciente;
import com.ERP.ERP.repository.FichaAtencionRepository;
import com.ERP.ERP.repository.MedicoRepository;
import com.ERP.ERP.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FichaAtencionService {

    @Autowired
    private FichaAtencionRepository fichaAtencionRepository;
    @Autowired
    private MedicoRepository doctorRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // Obtener todos los permisos
    public List<FichaAtencion> getAllPaciente() {
        return fichaAtencionRepository.findAll();
    }


}
