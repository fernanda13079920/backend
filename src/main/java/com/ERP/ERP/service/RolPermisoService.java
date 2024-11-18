package com.ERP.ERP.service;
import com.ERP.ERP.DTO.RolPermisoDTO;
import com.ERP.ERP.model.Permiso;
import com.ERP.ERP.model.Rol;
import com.ERP.ERP.model.RolPermiso;
import com.ERP.ERP.repository.PermisoRepository;
import com.ERP.ERP.repository.RolPermisoRepository;
import com.ERP.ERP.repository.RolRepository;
import com.ERP.ERP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RolPermisoService {

    @Autowired
    private RolPermisoRepository rolPermisoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PermisoRepository permisoRepository;

    public Optional<RolPermiso> findByIdRolAndIdPermiso(Long idPermiso, Long idRol) {
        return rolPermisoRepository.findByIdPermisoAndIdRol(idPermiso, idRol);
    }

    // Crear un nuevo permiso
    public  List<RolPermiso> store(List<RolPermisoDTO> lista) {
       List<RolPermiso> enviado= new ArrayList<>();

       lista.forEach(c -> {

           Optional<Rol> nrol=rolRepository.findById(c.getId_rol());
           Optional<Permiso> npermiso=PermisoRepository.findById(c.getId_permiso());
           Optional<RolPermiso> existing = findByIdRolAndIdPermiso(c.getId_permiso(), c.getId_rol());
           if (!existing.isPresent()) {
               RolPermiso nuevo = new RolPermiso();
               nuevo.setRol(nrol.get());
               nuevo.setPermiso(npermiso.get());
               nuevo = rolPermisoRepository.save(nuevo);
               enviado.add(nuevo);
           }

       });

       return enviado;

    }

    // Actualizar un permiso existente
    public List<Permiso> getList( Long id) {
        Optional<Usuario> nusuario = usuarioRepository.findById(id);
        Usuario  usuario = nusuario.get();
        Rol rol = usuario.getRol();
        List<Permiso> seleccionados = new ArrayList<>();
        List<RolPermiso> todos = rolPermisoRepository.findAll();
        todos.forEach(c->{
            if(c.getRol()==rol) {
                seleccionados.add(c.getPermiso());
            }
        });
        return seleccionados;
    }


}