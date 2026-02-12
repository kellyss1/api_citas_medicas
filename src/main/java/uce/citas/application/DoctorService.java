package uce.citas.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.citas.application.representation.DoctorRepresentation;
import uce.citas.domain.Doctor;
import uce.citas.infraestructure.DoctorInterface;

@ApplicationScoped
public class DoctorService {
    
    @Inject
    private DoctorInterface doctorInterface;

    @Transactional
    public void crear(DoctorRepresentation doctorRepresentation) {
        this.doctorInterface.persist(this.toEntity(doctorRepresentation));
    }

    public List<DoctorRepresentation> listar() {
        return this.doctorInterface.listAll().stream().map(this::toRepresentation).toList();
    }

    @Transactional
    public void actualizar(Integer id, DoctorRepresentation doctorRepresentation) {
        Doctor doctor = this.doctorInterface.findById(id.longValue());
        if (doctor != null) {
            doctor.setNombre(doctorRepresentation.getNombre());
            doctor.setEspecialidad(doctorRepresentation.getEspecialidad());
            //Actualizacion por dirty checking, no es necesario llamar a persist ya que el doctor ya esta siendo manejado por el EntityManager, 
            // por lo que cualquier cambio se guardara automaticamente al finalizar la transaccion
        }
    }

    @Transactional
    public void actualizarParcial(Integer id, DoctorRepresentation doctorRepresentation) {
        Doctor doctor = this.doctorInterface.findById(id.longValue());
        if (doctor != null) {
            if(doctorRepresentation.getNombre() != null) {
                doctor.setNombre(doctorRepresentation.getNombre());
            }
            if(doctorRepresentation.getEspecialidad() != null) {
                doctor.setEspecialidad(doctorRepresentation.getEspecialidad());
            }
            //Actualizacion por dirty checking
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        Doctor doctor = this.doctorInterface.findById(id.longValue());
        if (doctor != null) {
            this.doctorInterface.delete(doctor);
        }
    }

    private DoctorRepresentation toRepresentation(Doctor doctor) {
        DoctorRepresentation doctorRepresentation = new DoctorRepresentation();
        doctorRepresentation.setId(doctor.getId());
        doctorRepresentation.setNombre(doctor.getNombre());
        doctorRepresentation.setEspecialidad(doctor.getEspecialidad());
        return doctorRepresentation;
    } 

    private Doctor toEntity(DoctorRepresentation doctorRepresentation) {
        Doctor doctor = new Doctor();
        doctor.setNombre(doctorRepresentation.getNombre());
        doctor.setEspecialidad(doctorRepresentation.getEspecialidad());
        return doctor;
    }

}
