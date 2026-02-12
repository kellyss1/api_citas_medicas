package uce.citas.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.citas.domain.Paciente;

@ApplicationScoped
public class PacienteInterface implements PanacheRepository<Paciente> {
    
}
