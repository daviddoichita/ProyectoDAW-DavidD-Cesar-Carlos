package ies.camp.guardias.model.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import ies.camp.guardias.repository.entity.Cargo;
import ies.camp.guardias.repository.entity.Cuadrante;
import ies.camp.guardias.repository.entity.Sesion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuadranteDTO {

        private Long id;
        private String incidencias;
        private String firma;
        private Boolean deberes;
        private LocalDate fecha;
        private ProfesorDTO profesorDTO;
        private IntervaloDTO intervaloDTO;
        private CursoDTO cursoDTO;
        private CargoDTO cargoDTO;
        private Set<SesionDTO> sesionesDTO;

        /**
         * Recibe un Cuadrante y lo convierte a CuadranteDTO
         * 
         * @param cuadrante Cuadrante a convertir
         * @return cuadrante convertido a CuadranteDTO
         */
        public static CuadranteDTO convertToDTO(Cuadrante cuadrante) {
                CargoDTO cargoDTO = cuadrante.getCargo() == null ? null : CargoDTO.convertToDTO(cuadrante.getCargo());
                Set<SesionDTO> sesionesDTO = cuadrante.getSesiones() == null ? null
                                : cuadrante.getSesiones().stream().map(SesionDTO::convertToDTO)
                                                .collect(Collectors.toSet());

                return CuadranteDTO.builder()
                                .id(cuadrante.getId())
                                .incidencias(cuadrante.getIncidencias())
                                .firma(cuadrante.getFirma())
                                .deberes(cuadrante.getDeberes())
                                .fecha(cuadrante.getFecha())
                                .profesorDTO(ProfesorDTO.convertToDTO(cuadrante.getProfesor()))
                                .intervaloDTO(IntervaloDTO.convertToDTO(cuadrante.getIntervalo()))
                                .cursoDTO(CursoDTO.convertToDTO(cuadrante.getCurso()))
                                .cargoDTO(cargoDTO)
                                .sesionesDTO(sesionesDTO)
                                .build();
        }

        /**
         * Recibe un CuadranteDTO y lo convierte a Cuadrante
         * 
         * @param cuadranteDTO CuadranteDTO a convertir
         * @return cuadranteDTO convertido a Cuandrante
         */
        public static Cuadrante convertToEntity(CuadranteDTO cuadranteDTO) {
                Cargo cargo = cuadranteDTO.getCargoDTO() == null ? null
                                : CargoDTO.convertToEntity(cuadranteDTO.getCargoDTO());
                Set<Sesion> sesiones = cuadranteDTO.getSesionesDTO() == null ? null
                                : cuadranteDTO.getSesionesDTO().stream().map(SesionDTO::convertToEntity)
                                                .collect(Collectors.toSet());

                return Cuadrante.builder()
                                .id(cuadranteDTO.getId())
                                .incidencias(cuadranteDTO.getIncidencias())
                                .firma(cuadranteDTO.getFirma())
                                .deberes(cuadranteDTO.getDeberes())
                                .fecha(cuadranteDTO.getFecha())
                                .profesor(ProfesorDTO.convertToEntity(cuadranteDTO.getProfesorDTO()))
                                .intervalo(IntervaloDTO.convertToEntity(cuadranteDTO.getIntervaloDTO()))
                                .curso(CursoDTO.convertToEntity(cuadranteDTO.getCursoDTO()))
                                .cargo(cargo)
                                .sesiones(sesiones)
                                .build();
        }
}
