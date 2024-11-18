package com.ERP.ERP.service;
import com.ERP.ERP.model.Estudio;
import com.ERP.ERP.repository.EstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudioService {
    @Autowired
    private EstudioRepository estudioRepository;


    public Estudio createEstudio(Estudio estudio) {
        return estudioRepository.save(estudio);
    }


    public Estudio editEstudio(Long id, Estudio updatedEstudio) {
        Estudio estudio = estudioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudio no encontrado"));

        estudio.setNombre(updatedEstudio.getNombre());
        estudio.setDescripcion(updatedEstudio.getDescripcion());
        return estudioRepository.save(estudio);
    }

}
