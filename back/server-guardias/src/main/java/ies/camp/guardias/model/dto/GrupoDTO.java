package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Grupo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrupoDTO implements Serializable {

    private Long id;
    private Long numero;
    private String abreviacion;
    private String nombre;
    private String curso;

    /**
     * Convierte un Grupo a GrupoDTO sin la relacion con sesion
     * 
     * @param grupo Grupo a convertir a DTO
     * @return grupo convertido a DTO
     */
    public static GrupoDTO convertToDTO(Grupo grupo) {
        return GrupoDTO.builder()
                .id(grupo.getId())
                .numero(grupo.getNumero())
                .abreviacion(grupo.getAbreviacion())
                .nombre(grupo.getNombre())
                .curso(grupo.getCurso())
                .build();
    }

    /**
     * Convierte un GrupoDTO a Grupo
     * 
     * @param grupoDTO GrupoDTO a convertir a entidad
     * @return grupoDTO convertido a entidad
     */
    public static Grupo convertToEntity(GrupoDTO grupoDTO) {
        return Grupo.builder()
                .id(grupoDTO.getId())
                .numero(grupoDTO.getNumero())
                .abreviacion(grupoDTO.getAbreviacion())
                .nombre(grupoDTO.getNombre())
                .curso(grupoDTO.getCurso())
                .build();
    }
}
