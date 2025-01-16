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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sesion other = (Sesion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
