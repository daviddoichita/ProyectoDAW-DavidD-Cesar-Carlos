package ies.camp.guardias.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import ies.camp.guardias.repository.entity.Cuadrante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuadranteDTO implements Serializable {

    private Long id;
    private LocalDate fecha;
    private String incidencias;
    private String firma;
    private Boolean deberes;
    private CargoDTO cargo;
    private SesionDTO guardia;
    private Set<SesionDTO> faltas;

    /**
     * Convierte un Cuadrante a CuadranteDTO
     * 
     * @param cuadrante Cuadrante a convertir a DTO
     * @return cuadrante convertido a DTO
     */
    public static CuadranteDTO convertToDTO(Cuadrante cuadrante) {
        return CuadranteDTO.builder()
                .id(cuadrante.getId())
                .fecha(cuadrante.getFecha())
                .incidencias(cuadrante.getIncidencias())
                .firma(cuadrante.getFirma())
                .deberes(cuadrante.getDeberes())
                .cargo(CargoDTO.convertToDTO(cuadrante.getCargo()))
                .guardia(SesionDTO.convertToDTO(cuadrante.getGuardia()))
                .faltas(cuadrante.getFaltas().stream().map(SesionDTO::convertToDTO).collect(Collectors.toSet()))
                .build();
    }

    /**
     * Convierte un CuadranteDTO a Cuadrante
     * 
     * @param cuadranteDTO CuadranteDTO a convertir a entidad
     * @return cuadranteDTO convertido a entidad
     */
    public static Cuadrante convertToEntity(CuadranteDTO cuadranteDTO) {
        return Cuadrante.builder()
                .id(cuadranteDTO.getId())
                .fecha(cuadranteDTO.getFecha())
                .incidencias(cuadranteDTO.getIncidencias())
                .firma(cuadranteDTO.getFirma())
                .deberes(cuadranteDTO.getDeberes())
                .cargo(CargoDTO.convertToEntity(cuadranteDTO.getCargo()))
                .guardia(SesionDTO.convertToEntity(cuadranteDTO.getGuardia()))
                .faltas(cuadranteDTO.getFaltas().stream().map(SesionDTO::convertToEntity).collect(Collectors.toSet()))
                .build();
    }
}
