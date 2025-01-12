package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Materia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO implements Serializable {

    private Long id;
    private String nombre;

    /**
     * Recibe una Materia y la convierte a MateriaDTO sin ninguna relacion
     *
     * @param materia Materia a convertir
     * @return materia convertida a MateriaDTO
     */
    public static MateriaDTO convertToDTO(Materia materia) {
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombre(materia.getNombre())
                .build();
    }

    /**
     * Recibe una MateriaDTO y la convierte a Materia sin ninguna relacion
     *
     * @param materiaDTO MateriaDTO a convertir
     * @return materiaDTO convertida a Materia
     */
    public static Materia convertToEntity(MateriaDTO materiaDTO) {
        return Materia.builder()
                .id(materiaDTO.getId())
                .nombre(materiaDTO.getNombre())
                .build();
    }
}
