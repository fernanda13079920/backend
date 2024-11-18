package com.ERP.ERP.service;

import com.ERP.ERP.DTO.UsuarioCambioPasswordDTO;
import com.ERP.ERP.Security.PasswordGenerator;
import com.ERP.ERP.model.Usuarios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ERP.ERP.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }

    // Guardar un usuario
    public Usuarios save(Usuarios c) {
        return usuarioRepository.save(c);
    }

    @Transactional
    public Usuarios modificarContraseña(UsuarioCambioPasswordDTO c) {
        // Buscar el usuario por correo utilizando Optional
        Optional<Usuarios> optionalUsuario = usuarioRepository.findByCorreo(c.getCorreo());

        // Manejar el caso donde el usuario no existe
        Usuarios usuario = optionalUsuario.orElseThrow(() ->
                new RuntimeException("Usuario no encontrado con el correo proporcionado")
        );

        // Enviar correo electrónico con las credenciales
        String subject = "Cambio de Contraseña";
        String message = "Cambio de contraseña exitoso" + "!\n\n"
                + "Estas son tus credenciales de acceso:\n"
                + "Usuario: " + c.getCorreo() + "\n"
                + "Contraseña: " + c.getPassword() + "\n\n"
                + "Muchas Gracias por usar nuestros servicios";
        emailService.sendSimpleMessage(c.getCorreo(), subject, message);

        // Modificar y guardar la contraseña
        usuario.setPassword(passwordEncoder.encode(c.getPassword()));
        return usuarioRepository.save(usuario); // Guardar los cambios en la base de datos
    }

    // Guardar usuario
    public Usuarios guardarUsuario(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    // Generar una contraseña aleatoria
    public String generarContraseñaAleatoria() {
        // Usa el generador de contraseñas existente
        return PasswordGenerator.generateRandomPassword(12); // Puedes cambiar la longitud si lo necesitas
    }

    // Encriptar contraseña
    public String encriptarPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Verificar si un correo ya está registrado
    public boolean existsByCorreo(String correo) {
        // Usar Optional para verificar si el correo existe
        return usuarioRepository.findByCorreo(correo).isPresent();
    }
    public void eliminarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }
}
