package ies.camp.guardias.model.dto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ies.camp.guardias.repository.entity.Profesor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfesorDTO implements UserDetails {

    private Long id;
    private String nombre;
    private String apellidos;
    private Long numero;
    private String abreviacion;
    private String nif;
    @JsonIgnore
    private String contrasenya;
    private boolean admin;
    private String direccion;
    private Long telefono;
    private String email;
    @JsonIgnore
    private Boolean activo;
    @JsonIgnore
    private Set<RolDTO> roles;

    /**
     * Convierte un Profesor a ProfesorDTO sin la relacion con sesion
     *
     * @param profesor Profesor a convertir a DTO
     * @return profesor convertido a ProfesorDTO
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
                .admin(profesor.isAdmin())
                .direccion(profesor.getDireccion())
                .telefono(profesor.getTelefono())
                .email(profesor.getEmail())
                .activo(profesor.getActivo())
                .roles(profesor.getRoles().stream().map(RolDTO::convertToDTO).collect(Collectors.toSet()))
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
                .admin(profesorDTO.isAdmin())
                .direccion(profesorDTO.getDireccion())
                .telefono(profesorDTO.getTelefono())
                .email(profesorDTO.getEmail())
                .activo(profesorDTO.getActivo())
                .roles(profesorDTO.getRoles().stream().map(RolDTO::convertToEntity).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.contrasenya;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
