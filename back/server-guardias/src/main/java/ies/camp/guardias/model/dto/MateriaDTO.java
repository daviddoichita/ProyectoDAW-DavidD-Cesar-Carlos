package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Materia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateriaDTO implements Serializable {

    private Long id;
    private Long numero;
    private String abreviacion;
    private String nombre;
    private String codigo;
    private Integer horas;

    /**
     * Convierte una Materia a MateriaDTO sin la relacion con sesion
     * 
     * @param materia Materia a convertir a DTO
     * @return materia convertida a DTO
     */
    public static MateriaDTO convertToDTO(Materia materia) {
        return MateriaDTO.builder()
                .id(materia.getId())
                .numero(materia.getNumero())
                .abreviacion(materia.getAbreviacion())
                .nombre(materia.getNombre())
                .codigo(materia.getCodigo())
                .horas(materia.getHoras())
                .build();
    }

    /**
     * Convierte una MateriaDTO a Materia
     * 
     * @param materiaDTO MateriaDTO a convertir entidad
     * @return materiaDTO convertida a entidad
     */
    public static Materia convertToEntity(MateriaDTO materiaDTO) {
        return Materia.builder()
                .id(materiaDTO.getId())
                .numero(materiaDTO.getNumero())
                .abreviacion(materiaDTO.getAbreviacion())
                .nombre(materiaDTO.getNombre())
                .codigo(materiaDTO.getCodigo())
                .horas(materiaDTO.getHoras())
                .build();
    }
}