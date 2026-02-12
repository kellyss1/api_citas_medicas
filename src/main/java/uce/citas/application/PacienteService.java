package uce.citas.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.citas.application.representation.CitaRepresentation;
import uce.citas.application.representation.PacienteRepresentation;
import uce.citas.domain.Paciente;
import uce.citas.infraestructure.PacienteInterface;

@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteInterface pInterface;

    @Transactional
    public void crear(PacienteRepresentation pacienteRepresentation) {
        this.pInterface.persist(this.toEntity(pacienteRepresentation));
    }

    public List<PacienteRepresentation> listar() {
        return this.pInterface.listAll().stream().map(this::toRepresentation).toList();
    }

    public PacienteRepresentation listarPorId(Integer id) {
        Paciente paciente = this.pInterface.findById(id.longValue());
        if (paciente != null) {
            return this.toRepresentation(paciente);
        }
        return null;
    }

    public List<CitaRepresentation> listarCitasPorId(Integer id) {
        Paciente paciente = this.pInterface.findById(id.longValue());
        if (paciente != null) {
            return paciente.getCitas().stream().map(cita -> {
                CitaRepresentation citaRepresentation = new CitaRepresentation();
                citaRepresentation.setId(cita.getId());
                citaRepresentation.setFecha(cita.getFecha());
                citaRepresentation.setDoctorId(cita.getDoctor().getId().intValue());
                return citaRepresentation;
            }).toList();
        }
        return List.of();
    }

    @Transactional
    public void actualizar(Integer id, PacienteRepresentation pacienteRepresentation) {
        Paciente paciente = this.pInterface.findById(id.longValue());
        if (paciente != null) {
            paciente.setNombre(pacienteRepresentation.getNombre());
            paciente.setEdad(pacienteRepresentation.getEdad());
            paciente.setCedula(pacienteRepresentation.getCedula());
        }
        // Actualizacion por Dirty Checking
    }

    @Transactional
    public void actualizarParcial(Integer id, PacienteRepresentation pacienteRepresentation) {
        Paciente paciente = this.pInterface.findById(id.longValue());
        if (paciente != null) {
            if (pacienteRepresentation.getNombre() != null) {
                paciente.setNombre(pacienteRepresentation.getNombre());
            }
            if (pacienteRepresentation.getEdad() != null) {
                paciente.setEdad(pacienteRepresentation.getEdad());
            }
            if (pacienteRepresentation.getCedula() != null) {
                paciente.setCedula(pacienteRepresentation.getCedula());
            }
        }
        // Actualizacion por Dirty Checking
    }

    @Transactional
    public void eliminar(Integer id) {
        Paciente paciente = this.pInterface.findById(id.longValue());
        if (paciente != null) {
            this.pInterface.delete(paciente);
        }
    }

    private PacienteRepresentation toRepresentation(Paciente paciente) {
        PacienteRepresentation pacienteRepresentation = new PacienteRepresentation();
        pacienteRepresentation.setId(paciente.getId());
        pacienteRepresentation.setNombre(paciente.getNombre());
        pacienteRepresentation.setEdad(paciente.getEdad());
        pacienteRepresentation.setCedula(paciente.getCedula());

        return pacienteRepresentation;
    }

    private Paciente toEntity(PacienteRepresentation pacienteRepresentation) {
        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteRepresentation.getNombre());
        paciente.setEdad(pacienteRepresentation.getEdad());
        paciente.setCedula(pacienteRepresentation.getCedula());

        return paciente;
    }
}
