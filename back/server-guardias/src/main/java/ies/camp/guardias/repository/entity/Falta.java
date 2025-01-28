package ies.camp.guardias.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sesionfalta")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Falta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String incidencias;
    private String firma;
    private Boolean deberes;

    @ManyToOne
    @JoinColumn(name = "idcuadrante")
    @ToString.Exclude
    private Cuadrante cuadrante;

    @ManyToOne
    @JoinColumn(name = "idsesion")
    @ToString.Exclude
    private Sesion sesion;
}
