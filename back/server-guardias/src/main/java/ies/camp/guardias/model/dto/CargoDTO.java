package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CargoDTO implements Serializable {

    private Long id;
    private String nombre;
    private String abreviacion;

    /**
     * Convierte un Cargo a CargoDTO sin la relacion con sesion
     * 
     * @param cargo Cargo a convertir a DTO
     * @return cargo convertido a DTO
     */
    public static CargoDTO convertToDTO(Cargo cargo) {
        return CargoDTO.builder()
                .id(cargo.getId())
                .nombre(cargo.getNombre())
                .abreviacion(cargo.getAbreviacion())
                .build();
    }

    /**
     * Convierte un CargoDTO a Cargo
     * 
     * @param cargoDTO CargoDTO a convertir a entidad
     * @return cargoDTO convertido a entidad
     */
    public static Cargo convertToEntity(CargoDTO cargoDTO) {
        return Cargo.builder()
                .id(cargoDTO.getId())
                .nombre(cargoDTO.getNombre())
                .abreviacion(cargoDTO.getAbreviacion())
                .build();
    }
}
