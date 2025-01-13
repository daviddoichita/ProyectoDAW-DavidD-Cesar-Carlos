package ies.camp.guardias.model.dto;

import ies.camp.guardias.repository.entity.Aula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AulaDTO {

    private Long id;
    private String nombre;

    /**
     * Recibe un Aula y la convierte a AulaDTO sin ninguna relacion
     * 
     * @param aula Aula a convertir
     * @return aula convertida a AulaDTO
     */
    public static AulaDTO convertToDTO(Aula aula) {
        return AulaDTO.builder()
                .id(aula.getId())
                .nombre(aula.getNombre())
                .build();
    }

    /**
     * Recibe un AulaDTO y la convierte a Aula sin ninguna relacion
     * 
     * @param aulaDTO AulaDTO a convertir
     * @return aula convertida a Aula
     */
    public static Aula convertToEntity(AulaDTO aulaDTO) {
        return Aula.builder()
                .id(aulaDTO.getId())
                .nombre(aulaDTO.getNombre())
                .build();
    }
}
