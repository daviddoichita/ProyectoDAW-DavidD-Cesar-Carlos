package ies.camp.guardias.repository.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "sesion")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprofesor")
    @ToString.Exclude
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "idmateria")
    @ToString.Exclude
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "idgrupo")
    @ToString.Exclude
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "idaula")
    @ToString.Exclude
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "idintervalo")
    @ToString.Exclude
    private Intervalo intervalo;

    @ManyToOne
    @JoinColumn(name = "idcurso")
    @ToString.Exclude
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "iddia")
    @ToString.Exclude
    private Dia dia;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "faltas")
    @ToString.Exclude
    private Set<Cuadrante> cuadrantes;
}
