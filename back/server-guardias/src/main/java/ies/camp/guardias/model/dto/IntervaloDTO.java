package ies.camp.guardias.model.dto;

import java.io.Serializable;
import java.time.LocalTime;

import ies.camp.guardias.repository.entity.Intervalo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntervaloDTO implements Serializable {

    private Long id;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    /**
     * Convierte un Intervalo a IntervaloDTO sin la relacion con sesion
     * 
     * @param intervalo Intervalo a convertir a DTO
     * @return intervalo convertido a DTO
     */
    public static IntervaloDTO convertToDTO(Intervalo intervalo) {
        return IntervaloDTO.builder()
                .id(intervalo.getId())
                .horaInicio(intervalo.getHoraInicio())
                .horaFin(intervalo.getHoraFin())
                .build();
    }

    /**
     * Convierte un IntervaloDTO a Intervalo
     * 
     * @param intervaloDTO IntervaloDTO a convertir a entidad
     * @return intervaloDTO convertido a entidad
     */
    public static Intervalo convertToEntity(IntervaloDTO intervaloDTO) {
        return Intervalo.builder()
                .id(intervaloDTO.getId())
                .horaInicio(intervaloDTO.getHoraInicio())
                .horaFin(intervaloDTO.getHoraFin())
                .build();
    }
}