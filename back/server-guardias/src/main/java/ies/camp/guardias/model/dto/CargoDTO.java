package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoDTO implements Serializable {

    private Long id;
    private String nombre;

    /**
     * Recibe un Cargo y lo convierte a CargoDTO sin ninguna relacion
     *
     * @param cargo Cargo a convertir
     * @return cargo convertido a CargoDTO
     */
    public static CargoDTO convertToDTO(Cargo cargo) {
        return CargoDTO.builder()
                .id(cargo.getId())
                .nombre(cargo.getNombre())
                .build();
    }

    /**
     * Recibe un CargoDTO y lo convierte a Cargo sin ninguna relacion
     *
     * @param cargoDTO a convertir
     * @return cargoDTO convertido a Cargo
     */
    public static Cargo convertToEntity(CargoDTO cargoDTO) {
        return Cargo.builder()
                .id(cargoDTO.getId())
                .nombre(cargoDTO.getNombre())
                .build();
    }
}
