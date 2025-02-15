package ies.camp.guardias.model.dto;

import ies.camp.guardias.repository.entity.Rol;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    public static RolDTO convertToDTO(Rol rol) {
        return RolDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }

    public static Rol convertToEntity(RolDTO rolDTO) {
        return Rol.builder()
                .id(rolDTO.getId())
                .nombre(rolDTO.getNombre())
                .build();
    }
}
