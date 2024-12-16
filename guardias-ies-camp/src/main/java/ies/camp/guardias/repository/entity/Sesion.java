package ies.camp.guardias.repository.entity;

import java.util.List;

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
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprofesor")
    @ToString.Exclude
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "idgrupo")
    @ToString.Exclude
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "idintervalo")
    @ToString.Exclude
    private Intervalo intervalo;

    @ManyToOne
    @JoinColumn(name = "idmateria")
    @ToString.Exclude
    private Materia materia;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sesion")
    @ToString.Exclude
    private List<Cuadrante> listaCuadrantes;

    private String aula;
}
