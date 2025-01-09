package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Sesion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionDTO implements Serializable {

    private Long id;
    private String aula;
    private ProfesorDTO profesorDTO;
    private GrupoDTO grupoDTO;
    private MateriaDTO materiaDTO;
    private IntervaloDTO intervaloDTO;
    private DiaDTO diaDTO;

    /**
     * Recibe una Sesion y la convierte a SesionDTO
     *
     * @param sesion Sesion a convertir
     * @return sesion convertida a SesionDTO
     */
    public static SesionDTO convertToDTO(Sesion sesion) {
        return SesionDTO.builder()
                .id(sesion.getId())
                .aula(sesion.getAula())
                .profesorDTO(ProfesorDTO.convertToDTO(sesion.getProfesor()))
                .grupoDTO(GrupoDTO.convertToDTO(sesion.getGrupo()))
                .materiaDTO(MateriaDTO.convertToDTO(sesion.getMateria()))
                .intervaloDTO(IntervaloDTO.convertToDTO(sesion.getIntervalo()))
                .diaDTO(DiaDTO.convertToDTO(sesion.getDia()))
                .build();
    }

    /**
     * Recibe una SesionDTO y la convierte a Sesion
     *
     * @param sesionDTO SesionDTO a convertir
     * @return sesionDTO convertida a Sesion
     */
    public static Sesion convertToEntity(SesionDTO sesionDTO) {
        return Sesion.builder()
                .id(sesionDTO.getId())
                .aula(sesionDTO.getAula())
                .profesor(ProfesorDTO.convertToEntity(sesionDTO.getProfesorDTO()))
                .grupo(GrupoDTO.convertToEntity(sesionDTO.getGrupoDTO()))
                .materia(MateriaDTO.convertToEntity(sesionDTO.getMateriaDTO()))
                .intervalo(IntervaloDTO.convertToEntity(sesionDTO.getIntervaloDTO()))
                .dia(DiaDTO.convertToEntity(sesionDTO.getDiaDTO()))
                .build();
    }
}
