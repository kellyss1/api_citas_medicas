package uce.citas.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.citas.domain.Cita;

@ApplicationScoped
public class CitaInterface implements PanacheRepository<Cita> {
    
}
