package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Profesor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorDTO implements Serializable {

    private Long id;
    private String nombre;
    private String apellidos;
    private String nif;
    private String contrasenya;
    private Boolean admin;
    private String direccion;
    private Long telefono;
    private String email;

    /**
     * Recibe un Profesor y lo convierte a ProfesorDTO sin ninguna relacion
     * 
     * @param profesor Profesor a convertir
     * @return profesor convertido a ProfesorDTO
     */
    public static ProfesorDTO convertToDTO(Profesor profesor) {
        return ProfesorDTO.builder()
                .id(profesor.getId())
                .nombre(profesor.getNombre())
                .apellidos(profesor.getApellidos())
                .nif(profesor.getNif())
                .contrasenya(profesor.getContrasenya())
                .admin(profesor.getAdmin())
                .direccion(profesor.getDireccion())
                .telefono(profesor.getTelefono())
                .email(profesor.getEmail())
                .build();
    }

    /**
     * Recibe un ProfesorDTO y lo convierte a Profesor sin ninguna relacion
     * 
     * @param profesorDTO ProfesorDTO a convertir
     * @return profesorDTO convertido a Profesor
     */
    public static Profesor convertToEntity(ProfesorDTO profesorDTO) {
        return Profesor.builder()
                .id(profesorDTO.getId())
                .nombre(profesorDTO.getNombre())
                .apellidos(profesorDTO.getApellidos())
                .nif(profesorDTO.getNif())
                .contrasenya(profesorDTO.getContrasenya())
                .admin(profesorDTO.getAdmin())
                .direccion(profesorDTO.getDireccion())
                .telefono(profesorDTO.getTelefono())
                .email(profesorDTO.getEmail())
                .build();
    }
}
