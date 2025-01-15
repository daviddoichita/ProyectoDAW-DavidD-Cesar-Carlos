package ies.camp.guardias.model.dto;

import java.io.Serializable;

import ies.camp.guardias.repository.entity.Profesor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfesorDTO implements Serializable {

    private Long id;
    private String nombre;
    private String apellidos;
    private Long numero;
    private String abreviacion;
    private String nif;
    private String contrasenya;
    private Boolean admin;
    private String direccion;
    private Long telefono;
    private String email;
    private Boolean activo;

    /**
     * Convierte un Profesor a ProfesorDTO sin la relacion con sesion
     * 
     * @param profesor Profesor a convertir a DTO
     * @return profesor convertido a DTO
     */
    public static ProfesorDTO convertToDTO(Profesor profesor) {
        return ProfesorDTO.builder()
                .id(profesor.getId())
                .nombre(profesor.getNombre())
                .apellidos(profesor.getApellidos())
                .numero(profesor.getNumero())
                .abreviacion(profesor.getAbreviacion())
                .nif(profesor.getNif())
                .contrasenya(profesor.getContrasenya())
                .admin(profesor.getAdmin())
                .direccion(profesor.getDireccion())
                .telefono(profesor.getTelefono())
                .email(profesor.getEmail())
                .activo(profesor.getActivo())
                .build();
    }

    /**
     * Convierte un ProfesorDTO a Profesor
     * 
     * @param profesorDTO ProfesorDTO a convertir a entidad
     * @return profesorDTO convertido a entidad
     */
    public static Profesor convertToEntity(ProfesorDTO profesorDTO) {
        return Profesor.builder()
                .id(profesorDTO.getId())
                .nombre(profesorDTO.getNombre())
                .apellidos(profesorDTO.getApellidos())
                .numero(profesorDTO.getNumero())
                .abreviacion(profesorDTO.getAbreviacion())
                .nif(profesorDTO.getNif())
                .contrasenya(profesorDTO.getContrasenya())
                .admin(profesorDTO.getAdmin())
                .direccion(profesorDTO.getDireccion())
                .telefono(profesorDTO.getTelefono())
                .email(profesorDTO.getEmail())
                .activo(profesorDTO.getActivo())
                .build();
    }
}
