package ies.camp.guardias.model.dto;

import ies.camp.guardias.repository.entity.Falta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaltaDTO {

    private Long id;
    private String incidencias;
    private String firma;
    private Boolean deberes;
    private SesionDTO sesion;

    /**
     * Convierte una Falta a FaltaDTO
     *
     * @param falta Falta a convertir a DTO
     * @return falta convertida a DTO
     */
    public static FaltaDTO convertToDTO(Falta falta) {
        return FaltaDTO.builder()
                .id(falta.getId())
                .incidencias(falta.getIncidencias())
                .firma(falta.getFirma())
                .deberes(falta.getDeberes())
                .sesion(SesionDTO.convertToDTO(falta.getSesion()))
                .build();
    }

    /**
     * Convierte una FaltaDTO a Falta
     * 
     * @param faltaDTO FaltaDTO a convertir a entidad
     * @return faltaDTO convertida a entidad
     */
    public static Falta convertToEntity(FaltaDTO faltaDTO) {
        return Falta.builder()
                .id(faltaDTO.getId())
                .incidencias(faltaDTO.getIncidencias())
                .firma(faltaDTO.getFirma())
                .deberes(faltaDTO.getDeberes())
                .sesion(SesionDTO.convertToEntity(faltaDTO.getSesion()))
                .build();
    }
}
