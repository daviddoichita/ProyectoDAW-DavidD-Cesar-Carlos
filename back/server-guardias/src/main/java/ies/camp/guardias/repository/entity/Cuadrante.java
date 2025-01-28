package ies.camp.guardias.repository.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "cuadrante")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuadrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "idcargo")
    @ToString.Exclude
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "idguardia")
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private Sesion guardia;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cuadrante")
    @ToString.Exclude
    private Set<Falta> faltas;
}
