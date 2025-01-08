package ies.camp.guardias.model.dto;

import ies.camp.guardias.repository.entity.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDTO {

    private Long id;
    private String nombre;

    /**
     * Recibe un Curso y lo convierte a CursoDTO sin ninguna relacion
     *
     * @param curso Curso a convertir
     * @return curso convertido a CursoDTO
     */
    public static CursoDTO convertToDTO(Curso curso) {
        return CursoDTO.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .build();
    }

    /**
     * Recibe un CursoDTO y lo convierte a Curso sin ninguna relacion
     *
     * @param cursoDTO CursoDTO a convertir
     * @return cursoDTO convertido a Curso
     */
    public static Curso convertToEntity(CursoDTO cursoDTO) {
        return Curso.builder()
                .id(cursoDTO.getId())
                .nombre(cursoDTO.getNombre())
                .build();
    }
}
