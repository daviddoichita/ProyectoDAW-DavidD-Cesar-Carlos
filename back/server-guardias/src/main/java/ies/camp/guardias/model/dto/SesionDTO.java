package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Sesion;
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

        return SesionDTO.builder()
                .id(sesion.getId())
                .profesor(ProfesorDTO.convertToDTO(sesion.getProfesor()))
                .materia(MateriaDTO.convertToDTO(sesion.getMateria()))
                .grupo(GrupoDTO.convertToDTO(sesion.getGrupo()))
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
        return Sesion.builder()
                .id(sesionDTO.getId())
                .profesor(ProfesorDTO.convertToEntity(sesionDTO.getProfesor()))
                .materia(MateriaDTO.convertToEntity(sesionDTO.getMateria()))
                .grupo(GrupoDTO.convertToEntity(sesionDTO.getGrupo()))
                .aula(AulaDTO.convertToEntity(sesionDTO.getAula()))
                .intervalo(IntervaloDTO.convertToEntity(sesionDTO.getIntervalo()))
                .curso(CursoDTO.convertToEntity(sesionDTO.getCurso()))
                .dia(DiaDTO.convertToEntity(sesionDTO.getDia()))
                .build();
    }
}
