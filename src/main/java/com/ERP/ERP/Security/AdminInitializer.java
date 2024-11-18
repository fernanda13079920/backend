package com.ERP.ERP.Security;

import lombok.RequiredArgsConstructor;
import com.ERP.ERP.model.Rol;
import com.ERP.ERP.model.Usuarios;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ERP.ERP.service.RolService;
import com.ERP.ERP.service.UsuarioService;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        String usuario = "jfcv@gmail.com";
        String password = "Jfcv2024*";
        return args -> {
            // Verificar si existe el rol "ADMIN"
            Rol adminRole = rolService.findAll().stream()
                    .filter(role -> "Administrador".equals(role.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        // Si no existe, creamos el rol "Administrador"
                        Rol newAdminRole = new Rol();
                        newAdminRole.setName("Administrador");
                        return rolService.save(newAdminRole);
                    });

            // Verificar si ya existe un usuario administrador
            boolean adminExists = usuarioService.findAll().stream()
                    .anyMatch(user -> usuario.equals(user.getCorreo()));

            if (!adminExists) {
                // Crear el usuario administrador
                Usuarios adminUser = new Usuarios();
                adminUser.setCorreo(usuario);
                adminUser.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
                adminUser.setId_rol(adminRole);

                // Guardar el usuario en la base de datos
                usuarioService.save(adminUser);
                System.out.println("Usuario administrador creado: " + usuario + " " + password);
            } else {
                System.out.println("Usuario ya existe: " + usuario + " " + password);
            }
        };
    }

    @Bean
    public CommandLineRunner crearRoles() {
        return args -> {
            // Lista de roles a verificar y crear
            List<String> roles = Arrays.asList("Administrador", "Medico", "Paciente");

            for (String roleName : roles) {
                rolService.findAll().stream()
                        .filter(role -> roleName.equals(role.getName()))
                        .findFirst()
                        .orElseGet(() -> {
                            // Si no existe, creamos el rol
                            Rol newRole = new Rol();
                            newRole.setName(roleName);
                            return rolService.save(newRole);
                        });
            }
        };
    }
}
