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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cuadrante")
public class Cuadrante implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String incidencias;

    private String firma;

    private Boolean deberes;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "idprofesor")
    @ToString.Exclude
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "idintervalo")
    @ToString.Exclude
    private Intervalo intervalo;

    @ManyToOne
    @JoinColumn(name = "idcurso")
    @ToString.Exclude
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "idcargo")
    @ToString.Exclude
    private Cargo cargo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "cuadrantesesion", joinColumns = @JoinColumn(name = "idcuadrante"), inverseJoinColumns = @JoinColumn(name = "idsesion"))
    @ToString.Exclude
    private Set<Sesion> sesiones;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cuadrante other = (Cuadrante) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public Cuadrante clone() throws CloneNotSupportedException {
        return (Cuadrante) super.clone();
    }
}
