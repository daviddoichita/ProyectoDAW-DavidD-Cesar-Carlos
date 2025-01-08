package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Grupo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoDTO implements Serializable {

    private Long id;
    private String nombre;

    /**
     * Recibe un Grupo y lo convierte a GrupoDTO sin ninguna relacion
     *
     * @param grupo Grupo a convertir
     * @return grupo convertido a GrupoDTO
     */
    public static GrupoDTO convertToDTO(Grupo grupo) {
        return GrupoDTO.builder()
                .id(grupo.getId())
                .nombre(grupo.getNombre())
                .build();
    }

    /**
     * Recibe un GrupoDTO y lo convierte a Grupo sin ninguna relacion
     *
     * @param grupoDTO GrupoDTO a convertir
     * @return grupoDTO convertido a Grupo
     */
    public static Grupo convertToEntity(GrupoDTO grupoDTO) {
        return Grupo.builder()
                .id(grupoDTO.getId())
                .nombre(grupoDTO.getNombre())
                .build();
    }
}
