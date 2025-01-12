package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Dia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaDTO implements Serializable {

    private Long id;
    private String nombre;

    /**
     * Recibe un Dia y lo convierte a DiaDTO sin ninguna relacion
     *
     * @param dia Dia a convertir
     * @return dia convertido a DiaDTO
     */
    public static DiaDTO convertToDTO(Dia dia) {
        return DiaDTO.builder()
                .id(dia.getId())
                .nombre(dia.getNombre())
                .build();
    }

    /**
     * Recibe un DiaDTO y lo convertir a Dia sin ninguna relacion
     *
     * @param diaDTO DiaDTO a convertir
     * @return diaDTO convertido a Dia
     */
    public static Dia convertToEntity(DiaDTO diaDTO) {
        return Dia.builder()
                .id(diaDTO.getId())
                .nombre(diaDTO.getNombre())
                .build();
    }
}
