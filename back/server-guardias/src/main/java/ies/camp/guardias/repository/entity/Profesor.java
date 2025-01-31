package ies.camp.guardias.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profesor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private Long numero;

    @EqualsAndHashCode.Include
    private String abreviacion;

    private String nif;

    @Column(nullable = false)
    private String contrasenya;

    private boolean admin;
    private String direccion;
    private Long telefono;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean activo;

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "profesor"
    )
    private Set<Sesion> sesiones;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "profesor_roles",
        joinColumns = @JoinColumn(name = "idprofesor"),
        inverseJoinColumns = @JoinColumn(name = "idrol")
    )
    private Set<Rol> roles;
}
