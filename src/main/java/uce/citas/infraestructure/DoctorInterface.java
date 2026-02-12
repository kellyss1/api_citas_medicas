package uce.citas.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.citas.domain.Doctor;

@ApplicationScoped
public class DoctorInterface implements PanacheRepository<Doctor> {
    
}
