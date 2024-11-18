package com.ERP.ERP.service;

import com.ERP.ERP.model.Paciente;
import com.ERP.ERP.model.Rol;
import com.ERP.ERP.model.Usuarios;
import com.ERP.ERP.repository.PacienteRepository;
import com.ERP.ERP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public void registrarPacientesDesdeExcel(List<Paciente> pacientesExcel) {
        List<Paciente> pacientesRegistrados = pacienteRepository.findAll();

        // Desactivar pacientes que no están en el Excel
        List<Paciente> pacientesInactivos = pacientesRegistrados.stream()
                .filter(p -> pacientesExcel.stream().noneMatch(pe -> pe.getCi().equals(p.getCi())))
                .toList();

        pacientesInactivos.forEach(p -> {
            p.setEstado(false);
            pacienteRepository.save(p);
        });

        // Procesar pacientes del Excel
        for (Paciente pacienteExcel : pacientesExcel) {
            Optional<Paciente> optionalPaciente = pacienteRepository.findByCi(pacienteExcel.getCi());
            if (optionalPaciente.isPresent()) {
                // Si el paciente ya está registrado, se asegura que esté activo
                Paciente pacienteExistente = optionalPaciente.get();
                pacienteExistente.setEstado(true);
                pacienteRepository.save(pacienteExistente);
            } else {
                // Si el paciente no existe, se registra junto con su usuario
                String password = usuarioService.generarContraseñaAleatoria();
                Usuarios usuario = new Usuarios();
                usuario.setCorreo(pacienteExcel.getEmail());
                usuario.setPassword(passwordEncoder.encode(password));
                usuario.setRol(new Rol(3L)); // Asignar rol Paciente
                usuario = usuarioRepository.save(usuario);

                pacienteExcel.setUsuario(usuario);
                pacienteRepository.save(pacienteExcel);

                // Enviar credenciales al correo
                String mensaje = String.format(
                        "Hola %s, estas son tus credenciales de acceso:\nCorreo: %s\nContraseña: %s\n\nPor favor, no compartas esta información.",
                        pacienteExcel.getNombre(),
                        pacienteExcel.getEmail(),
                        password
                );
                emailService.sendSimpleMessage(pacienteExcel.getEmail(), "Credenciales de Acceso", mensaje);
            }
        }
    }
}
