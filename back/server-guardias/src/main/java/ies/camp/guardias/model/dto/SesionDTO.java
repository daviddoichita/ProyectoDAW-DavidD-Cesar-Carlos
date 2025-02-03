package ies.camp.guardias.model.dto;

import ies.camp.guardias.repository.entity.Grupo;
import ies.camp.guardias.repository.entity.Sesion;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SesionDTO implements Serializable {

    private Long id;
    private ProfesorDTO profesor;
    private MateriaDTO materia;
    private GrupoDTO grupo;
    private AulaDTO aula;
    private IntervaloDTO intervalo;
    private CursoDTO curso;
    private DiaDTO dia;

    /**
     * Convierte una Sesion a SesionDTO
     *
     * @param sesion Sesion a convertir a DTO
     * @return sesion convertida a DTO
     */
    public static SesionDTO convertToDTO(Sesion sesion) {
        GrupoDTO grupoDTO = sesion.getGrupo() == null
                ? null
                : GrupoDTO.convertToDTO(sesion.getGrupo());

        return SesionDTO.builder()
                .id(sesion.getId())
                .profesor(ProfesorDTO.convertToDTO(sesion.getProfesor()))
                .materia(MateriaDTO.convertToDTO(sesion.getMateria()))
                .grupo(grupoDTO)
                .aula(AulaDTO.convertToDTO(sesion.getAula()))
                .intervalo(IntervaloDTO.convertToDTO(sesion.getIntervalo()))
                .curso(CursoDTO.convertToDTO(sesion.getCurso()))
                .dia(DiaDTO.convertToDTO(sesion.getDia()))
                .build();
    }

    /**
     * Convierte una SesionDTO a Sesion
     *
     * @param sesionDTO SesionDTO a convertir a entidad
     * @return sesionDTO convertida a entidad
     */
    public static Sesion convertToEntity(SesionDTO sesionDTO) {
        Grupo grupo = sesionDTO.getGrupo() == null
                ? null
                : GrupoDTO.convertToEntity(sesionDTO.getGrupo());

        return Sesion.builder()
                .id(sesionDTO.getId())
                .profesor(ProfesorDTO.convertToEntity(sesionDTO.getProfesor()))
                .materia(MateriaDTO.convertToEntity(sesionDTO.getMateria()))
                .grupo(grupo)
                .aula(AulaDTO.convertToEntity(sesionDTO.getAula()))
                .intervalo(IntervaloDTO.convertToEntity(sesionDTO.getIntervalo()))
                .curso(CursoDTO.convertToEntity(sesionDTO.getCurso()))
                .dia(DiaDTO.convertToEntity(sesionDTO.getDia()))
                .build();
    }
}
