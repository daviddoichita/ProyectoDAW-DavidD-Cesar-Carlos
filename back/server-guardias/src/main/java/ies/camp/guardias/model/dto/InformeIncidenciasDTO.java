package ies.camp.guardias.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import ies.camp.guardias.repository.entity.Intervalo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformeIncidenciasDTO implements Serializable {

    private LocalDate fecha;
    private Intervalo intervalo;
    private String nombreProfesor;
    private String apellidosProfesor;
    private String textoIncidencia;
}