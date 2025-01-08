package ies.camp.guardias.model.dto;

import java.io.Serializable;
import java.time.LocalTime;

import ies.camp.guardias.repository.entity.Intervalo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntervaloDTO implements Serializable {

    private Long id;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    /**
     * Recibe un Intervalo y lo convierte a IntervaloDTO sin ninguna relacion
     *
     * @param intervalo Intervalo a convertir
     * @return intervalo convertido a IntervaloDTO
     */
    public static IntervaloDTO convertToDTO(Intervalo intervalo) {
        return IntervaloDTO.builder()
                .id(intervalo.getId())
                .horaInicio(intervalo.getHoraInicio())
                .horaFin(intervalo.getHoraFin())
                .build();
    }

    /**
     * Recibe un IntervaloDTO y lo convierte a Intervalo sin ninguna relacion
     *
     * @param intervaloDTO IntervaloDTO a convertir
     * @return intervaloDTO convertido a Intervalo
     */
    public static Intervalo convertToEntity(IntervaloDTO intervaloDTO) {
        return Intervalo.builder()
                .id(intervaloDTO.getId())
                .horaInicio(intervaloDTO.getHoraInicio())
                .horaFin(intervaloDTO.getHoraFin())
                .build();
    }
}
