package uce.citas.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.citas.application.representation.CitaRepresentation;
import uce.citas.domain.Cita;
import uce.citas.infraestructure.CitaInterface;
import uce.citas.infraestructure.DoctorInterface;
import uce.citas.infraestructure.PacienteInterface;

@ApplicationScoped
public class CitaService {
    
    @Inject
    private CitaInterface citaInterface;

    @Inject
    private DoctorInterface doctorInterface;

    @Inject
    private PacienteInterface pacienteInterface;

    @Transactional
    public void crearCita(CitaRepresentation citaRepresentation) {
        this.citaInterface.persist(toEntity(citaRepresentation));
    }

    public List<CitaRepresentation> listarCitas(){
        return this.citaInterface.listAll().stream().map(this::toRepresentation).toList();
    }

    @Transactional
    public void actualizarCita(Integer id, CitaRepresentation citaRepresentation){
        Cita cita = this.citaInterface.findById(id.longValue());
        if (cita != null) {
            cita.setFecha(citaRepresentation.getFecha());
            cita.setHora(citaRepresentation.getHora());
            cita.setDoctor(this.doctorInterface.findById(citaRepresentation.getDoctorId().longValue()));
            cita.setPaciente(this.pacienteInterface.findById(citaRepresentation.getPacienteId().longValue()));
        }
        // Actualizacion por Dirty Checking
    }

    @Transactional
    public void actualizarCitaParcial(Integer id, CitaRepresentation citaRepresentation){
        Cita cita = this.citaInterface.findById(id.longValue());
        if (cita != null) {
            if(citaRepresentation.getFecha() != null) {
                cita.setFecha(citaRepresentation.getFecha());
            }
            if(citaRepresentation.getHora() != null) {
                cita.setHora(citaRepresentation.getHora());
            }
            if(citaRepresentation.getDoctorId() != null) {
                cita.setDoctor(this.doctorInterface.findById(citaRepresentation.getDoctorId().longValue()));
            }
            if(citaRepresentation.getPacienteId() != null) {
                cita.setPaciente(this.pacienteInterface.findById(citaRepresentation.getPacienteId().longValue()));
            }
        }
        // Actualizacion por Dirty Checking
    }

    @Transactional
    public void eliminarCita(Integer id) {
        this.citaInterface.deleteById(id.longValue());
    }

    private Cita toEntity(CitaRepresentation citaRepresentation) {
        Cita cita = new Cita();
        cita.setId(citaRepresentation.getId());
        cita.setFecha(citaRepresentation.getFecha());
        cita.setHora(citaRepresentation.getHora());
        // Validar doctor
        if (citaRepresentation.getDoctorId() == null) {
            throw new IllegalArgumentException("DoctorId es obligatorio para crear una cita.");
        }
        var doctor = this.doctorInterface.findById(citaRepresentation.getDoctorId().longValue());
        if (doctor == null) {
            throw new IllegalArgumentException("No existe un doctor con el id proporcionado.");
        }
        cita.setDoctor(doctor);
        // Validar paciente
        if (citaRepresentation.getPacienteId() == null) {
            throw new IllegalArgumentException("PacienteId es obligatorio para crear una cita.");
        }
        var paciente = this.pacienteInterface.findById(citaRepresentation.getPacienteId().longValue());
        if (paciente == null) {
            throw new IllegalArgumentException("No existe un paciente con el id proporcionado.");
        }
        cita.setPaciente(paciente);
        return cita;
    }

    private CitaRepresentation toRepresentation(Cita cita){
        CitaRepresentation citaRepresentation = new CitaRepresentation();
        citaRepresentation.setId(cita.getId());
        citaRepresentation.setFecha(cita.getFecha());
        citaRepresentation.setHora(cita.getHora());
        citaRepresentation.setDoctorId(cita.getDoctor().getId().intValue());
        citaRepresentation.setPacienteId(cita.getPaciente().getId().intValue());
        return citaRepresentation;
    }
}
