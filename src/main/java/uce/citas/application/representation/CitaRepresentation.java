package uce.citas.application.representation;

import lombok.Data;

@Data
public class CitaRepresentation {
    
    private Integer id;
    private String fecha;
    private String hora;

    private Integer pacienteId;
    private Integer doctorId;
}
