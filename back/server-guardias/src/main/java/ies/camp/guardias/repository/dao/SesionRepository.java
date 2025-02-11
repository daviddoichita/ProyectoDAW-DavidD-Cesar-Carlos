package ies.camp.guardias.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Sesion;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface SesionRepository extends JpaRepository<Sesion, Long> {

    @Query(value = "SELECT * FROM sesion WHERE idgrupo = (SELECT id FROM grupo WHERE nombre = 'GUARDIAS')", nativeQuery = true)
    public List<Sesion> findSesionesGuardia();

    @Query(value = "SELECT * FROM sesoin WHERE iddia = (SELECT id FROM dia WHERE abreviacion = ?1)", nativeQuery = true)
    public List<Sesion> findByAbreviacionDia(String abreviacion);

}
