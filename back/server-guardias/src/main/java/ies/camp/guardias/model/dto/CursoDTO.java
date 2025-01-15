package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CursoDTO implements Serializable {

    private Long id;
    private String nombre;
    private String abreviacion;

    /**
     * Convierte un Curso a CursoDTO sin la relacion con sesion
     * 
     * @param curso Curso a convertir a DTO
     * @return curso convertido a DTO
     */
    public static CursoDTO convertToDTO(Curso curso) {
        return CursoDTO.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .abreviacion(curso.getAbreviacion())
                .build();
    }

    /**
     * Convierte un CursoDTO a Curso
     * 
     * @param cursoDTO CursoDTO a convertir a entidad
     * @return cursoDTO convertido a entidad
     */
    public static Curso convertToEntity(CursoDTO cursoDTO) {
        return Curso.builder()
                .id(cursoDTO.getId())
                .nombre(cursoDTO.getNombre())
                .abreviacion(cursoDTO.getAbreviacion())
                .build();
    }
}
