package uce.citas.application.representation;

import java.util.List;

import lombok.Data;

@Data
public class PacienteRepresentation {
    
    private Integer id;
    private String nombre;
    private String edad;
    private String cedula;

    private List<LinkDto> link;
}
