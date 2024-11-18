package com.ERP.ERP.service;

import com.ERP.ERP.DTO.PrescripcionDTO;
import com.ERP.ERP.DTO.SesionCrearDTO;
import com.ERP.ERP.DTO.SesionEditarDTO;
import com.ERP.ERP.DTO.SesionEstudioDTO;
import com.ERP.ERP.model.*;
import com.ERP.ERP.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;
    @Autowired
    private PacienteRepository pacienteRepository;


    @Autowired
    private EstudioRepository estudioRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private PrescripcionRepository prescripcionRepository;


    @Autowired
    private PrescripcionService prescripcionService;
    @Autowired
    private FichaAtencionRepository ficha_AtencionRepository;

    @Autowired
    private SesionEstudioRepository sesionEstudioRepository;

    private static final int MAX_SESSION_DURATION_MINUTES = 2; // Define la duración máxima de la sesión en minutos


     //Método para iniciar una nueva sesión para un paciente


    @Transactional
    public Sesion startSesion(Long fichaAtencionId, SesionCrearDTO sesionCrearDTO) {

        FichaAtencion fichaAtencion = ficha_AtencionRepository.findById(fichaAtencionId)
                .orElseThrow(() -> new RuntimeException("FichaAtencion no encontrada"));
        if (sesionCrearDTO.getFechaHoraInicio().isBefore(fichaAtencion.getFechaHoraInicio()) || sesionCrearDTO.getFechaHoraCierre().isAfter(fichaAtencion.getFechaHoraCierre())) {
            throw new RuntimeException("La fecha y hora de la sesión excede la fecha y hora de cierre de la ficha de atención");
        }

        Optional<Historial> optionalHistorial = historialRepository.findByPaciente_Id(fichaAtencion.getPaciente().getId());
        Historial historial;
        if (optionalHistorial.isPresent()) {
            historial = optionalHistorial.get();
        } else {
            Paciente paciente = pacienteRepository.findById(fichaAtencion.getPaciente().getId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            historial = new Historial();
            historial.setPaciente(paciente);
            historial.setFechaCreacion(LocalDate.now());
          //  historial.setFechaActualizacion(LocalDate.now());
            historial.setDescripcion("Historial inicial para " + paciente.getNombre());
            historial = historialRepository.save(historial);
        }

        Sesion sesion = new Sesion();
        sesion.setFechaHoraInicio(sesionCrearDTO.getFechaHoraInicio());
        sesion.setFechaHoraCierre(sesionCrearDTO.getFechaHoraCierre());
        sesion.setHistorial(historial);
        return sesionRepository.save(sesion);
    }

    // Método para agregar una lista de prescripciones a una sesión.
    @Transactional
    public List<Prescripcion> addPrescriptions(Long sesionId, List<PrescripcionDTO> prescripcionesDTO) {
        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada o ya cerrada"));

        List<Prescripcion> prescripciones = new ArrayList<>();
        for (PrescripcionDTO prescripcionDTO : prescripcionesDTO) {
            Prescripcion prescripcion = new Prescripcion();
            Medicamento medicamento = medicamentoRepository.findById(prescripcionDTO.getId_medicamento())
                    .orElseThrow(() -> new RuntimeException("Sesión no encontrada o ya cerrada"));

            prescripcion.setMedicamento(medicamento);
            prescripcion.setIndicacion(prescripcionDTO.getIndicacion());
            prescripcion.setSesion(sesion);
            prescripciones.add(prescripcion);
        }
        return prescripcionRepository.saveAll(prescripciones);
    }



    @Transactional
    public List<SesionEstudio> addSesionEstudios(Long sesionId, List<SesionEstudioDTO> sesionEstudiosDTO) {
        Sesion sesion = sesionRepository.findById(sesionId).orElseThrow(() -> new RuntimeException("Sesión no encontrada o ya cerrada"));
        List<SesionEstudio> sesionEstudios = sesionEstudiosDTO.stream().map(sesionEstudioDTO -> {
            Estudio estudio = estudioRepository.findById(sesionEstudioDTO.getId_estudio()).orElseThrow(() -> new RuntimeException("Estudio no encontrado"));
            SesionEstudio sesionEstudio = new SesionEstudio();
            sesionEstudio.setSesion(sesion);
            sesionEstudio.setEstudio(estudio);
            sesionEstudio.setComentario(sesionEstudioDTO.getComentario());
            return sesionEstudio;
        }).collect(Collectors.toList());

        return sesionEstudioRepository.saveAll(sesionEstudios);
    }

     //Método para obtener todas las sesiones previas de un paciente

    public List<Sesion> getPreviousSessionsForPaciente(Long pacienteId) {
        return sesionRepository.findByHistorial_Paciente_Id(pacienteId);
    }


    @Transactional
    public Sesion guardarSesionCompleta(Long fichaAtencionId, SesionCrearDTO sesionCrearDTO) {
        Sesion sesion= startSesion(fichaAtencionId,sesionCrearDTO);
        FichaAtencion fichaAtencion = ficha_AtencionRepository.findById(fichaAtencionId)
                .orElseThrow(() -> new RuntimeException("FichaAtencion no encontrada"));
        Historial historial = historialRepository.findByPaciente_Id(fichaAtencion.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        sesion.setFichaAtencion(fichaAtencion);
        sesion.setHistorial(historial);

        List<Prescripcion> nuevasPrescripciones = addPrescriptions(sesion.getId(), sesionCrearDTO.getPrescripciones());
        List<SesionEstudio> nuevasSesionEstudios = addSesionEstudios(sesion.getId(), sesionCrearDTO.getSesionEstudios());
        String observacion = sesionCrearDTO.getObservacion();
        String diagnostico = sesionCrearDTO.getDiagnostico();
        String documento = sesionCrearDTO.getDocumento();
        sesion.setObservacion(observacion);
        sesion.setDiagnostico(diagnostico);
        sesion.setDocumento(documento);
        sesion.setFechaHoraCierre(sesionCrearDTO.getFechaHoraCierre());
        sesion.setFrecuenciaCardiaca(sesionCrearDTO.getFrecuenciaCardiaca());
        sesion.setTemperaturaCorporal(sesionCrearDTO.getTemperaturaCorporal());
        sesion.setPesoCorporal(sesionCrearDTO.getPesoCorporal());
        sesion.setPresionArterial(sesionCrearDTO.getPresionArterial());
        historial.setFechaActualizacion(LocalDate.now());
        sesionRepository.save(sesion);
        return sesion;
    }


    public Sesion getSesionById(Long sesionId) {
        return sesionRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
    }

     public Sesion editarSesionCompleta(Long sesionId, SesionEditarDTO sesionEditarDTO) {

        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if ( sesionEditarDTO.getFechaHoraCierre().isAfter(sesion.getFichaAtencion().getFechaHoraCierre())) {
            throw new RuntimeException("La fecha y hora de la sesión excede la fecha y hora de cierre de la ficha de atención");
        }

       // deletePrescriptionsBySesionId(sesionId);
        List<Prescripcion> nuevasPrescripciones = addPrescriptions(sesion.getId(), sesionEditarDTO.getPrescripciones());
      //  deleteSesionEstudiosBySesionId(sesionId);
        List<SesionEstudio> nuevasSesionEstudios = addSesionEstudios(sesion.getId(), sesionEditarDTO.getSesionEstudios());
        String observacion = sesionEditarDTO.getObservacion();
        String diagnostico = sesionEditarDTO.getDiagnostico();
        String documento = sesionEditarDTO.getDocumento();
        sesion.setObservacion(observacion);
        sesion.setDiagnostico(diagnostico);
        sesion.setDocumento(documento);
        sesion.setFechaHoraCierre(sesionEditarDTO.getFechaHoraCierre());
        sesion.setFrecuenciaCardiaca(sesionEditarDTO.getFrecuenciaCardiaca());
        sesion.setTemperaturaCorporal(sesionEditarDTO.getTemperaturaCorporal());
        sesion.setPesoCorporal(sesionEditarDTO.getPesoCorporal());
        sesion.setPresionArterial(sesionEditarDTO.getPresionArterial());
        //historial.setFechaActualizacion(LocalDate.now());
        sesionRepository.save(sesion);
        return sesion;
    }

    public List<Sesion> getAllSessionsByPatientId(Long patientId) {
        return sesionRepository.findByHistorial_Paciente_Id(patientId);
    }





}
