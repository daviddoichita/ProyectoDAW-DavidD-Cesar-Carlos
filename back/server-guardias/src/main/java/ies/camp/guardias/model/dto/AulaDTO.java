package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Aula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AulaDTO implements Serializable {

    private Long id;
    private Long numero;
    private String abreviacion;
    private String nombre;

    /**
     * Convierte un Aula a AulaDTO sin la relacion con sesion
     * 
     * @param aula Aula a convertir a DTO
     * @return aula convertida a DTO
     */
    public static AulaDTO convertToDTO(Aula aula) {
        return AulaDTO.builder()
                .id(aula.getId())
                .numero(aula.getNumero())
                .abreviacion(aula.getAbreviacion())
                .nombre(aula.getNombre())
                .build();
    }

    /**
     * Convierte un AulaDTO a Aula
     * 
     * @param aulaDTO AulaDTO a convertir a entidad
     * @return aulaDTO convertida a entidad
     */
    public static Aula convertToEntity(AulaDTO aulaDTO) {
        return Aula.builder()
                .id(aulaDTO.getId())
                .numero(aulaDTO.getNumero())
                .abreviacion(aulaDTO.getNombre())
                .nombre(aulaDTO.getNombre())
                .build();
    }
}
