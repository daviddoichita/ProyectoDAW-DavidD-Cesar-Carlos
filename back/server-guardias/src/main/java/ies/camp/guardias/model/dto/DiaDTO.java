package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Dia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaDTO implements Serializable {

    private Long id;
    private String nombre;
    private String abreviacion;

    /**
     * Convierte un Dia a DiaDTO sin la relacion con sesion
     * 
     * @param dia Dia a convertir a DTO
     * @return dia convertido a DTO
     */
    public static DiaDTO convertToDTO(Dia dia) {
        return DiaDTO.builder()
                .id(dia.getId())
                .nombre(dia.getNombre())
                .abreviacion(dia.getAbreviacion())
                .build();
    }

    /**
     * Convierte un DiaDTO a Dia
     * 
     * @param diaDTO DiaDTO a convertir a entidad
     * @return diaDTO convertido a entidad
     */
    public static Dia convertToEntity(DiaDTO diaDTO) {
        return Dia.builder()
                .id(diaDTO.getId())
                .nombre(diaDTO.getNombre())
                .abreviacion(diaDTO.getAbreviacion())
                .build();
    }
}
