package com.ERP.ERP.service;

import com.ERP.ERP.model.Medico;
import com.ERP.ERP.model.Usuarios;
import com.ERP.ERP.repository.MedicoRepository;
import com.ERP.ERP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos los médicos
    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    // Guardar un médico
    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    // Buscar médico por ID
    public Optional<Medico> findById(Long id) {
        return medicoRepository.findById(id);
    }

    // Eliminar médico por ID
    @Transactional
    public void deleteById(Long id) {
        medicoRepository.deleteById(id);
    }

    // Eliminar usuario por ID (opcional)
    public void deleteUsuarioById(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}
