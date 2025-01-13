package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Aula;
import ies.camp.guardias.repository.entity.Grupo;
import ies.camp.guardias.repository.entity.Materia;
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
    private ProfesorDTO profesorDTO;
    private GrupoDTO grupoDTO;
    private MateriaDTO materiaDTO;
    private IntervaloDTO intervaloDTO;
    private DiaDTO diaDTO;
    private AulaDTO aulaDTO;

    /**
     * Recibe una Sesion y la convierte a SesionDTO
     *
     * @param sesion Sesion a convertir
     * @return sesion convertida a SesionDTO
     */
    public static SesionDTO convertToDTO(Sesion sesion) {
        GrupoDTO grupoDTO = sesion.getGrupo() == null ? null : GrupoDTO.convertToDTO(sesion.getGrupo());
        MateriaDTO materiaDTO = sesion.getMateria() == null ? null : MateriaDTO.convertToDTO(sesion.getMateria());
        AulaDTO aulaDTO = sesion.getAula() == null ? null : AulaDTO.convertToDTO(sesion.getAula());

        return SesionDTO.builder()
                .id(sesion.getId())
                .profesorDTO(ProfesorDTO.convertToDTO(sesion.getProfesor()))
                .grupoDTO(grupoDTO)
                .materiaDTO(materiaDTO)
                .intervaloDTO(IntervaloDTO.convertToDTO(sesion.getIntervalo()))
                .diaDTO(DiaDTO.convertToDTO(sesion.getDia()))
                .aulaDTO(aulaDTO)
                .build();
    }

    /**
     * Recibe una SesionDTO y la convierte a Sesion
     *
     * @param sesionDTO SesionDTO a convertir
     * @return sesionDTO convertida a Sesion
     */
    public static Sesion convertToEntity(SesionDTO sesionDTO) {
        Grupo grupo = sesionDTO.getGrupoDTO() == null ? null : GrupoDTO.convertToEntity(sesionDTO.getGrupoDTO());
        Materia materia = sesionDTO.getMateriaDTO() == null ? null
                : MateriaDTO.convertToEntity(sesionDTO.getMateriaDTO());
        Aula aula = sesionDTO.getAulaDTO() == null ? null : AulaDTO.convertToEntity(sesionDTO.getAulaDTO());

        return Sesion.builder()
                .id(sesionDTO.getId())
                .profesor(ProfesorDTO.convertToEntity(sesionDTO.getProfesorDTO()))
                .grupo(grupo)
                .materia(materia)
                .intervalo(IntervaloDTO.convertToEntity(sesionDTO.getIntervaloDTO()))
                .dia(DiaDTO.convertToEntity(sesionDTO.getDiaDTO()))
                .aula(aula)
                .build();
    }
}
