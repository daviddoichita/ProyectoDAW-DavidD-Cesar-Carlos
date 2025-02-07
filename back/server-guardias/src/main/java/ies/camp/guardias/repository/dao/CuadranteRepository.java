package ies.camp.guardias.repository.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Cuadrante;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CuadranteRepository extends JpaRepository<Cuadrante, Long> {

    @Query(value = "SELECT * FROM cuadrante WHERE fecha >= ?1 AND fecha <= ?2", nativeQuery = true)
    List<Cuadrante> findByRange(LocalDate fechaInicio, LocalDate fechaFin);

    @Query(value = "SELECT * from cuadrante where id = any (select idcuadrante from sesionfalta)", nativeQuery = true)
    List<Cuadrante> findCuadrantesConFaltas();

    @Query(value = "SELECT c.* from cuadrante c where c.id = any (SELECT idcuadrante from sesionfalta f where f.firma is null)", nativeQuery = true)
    List<Cuadrante> findCuadrantesSinFirmar();

    @Query(value = "SELECT * from cuadrante where id = any (SELECT idcuadrante from sesionfalta where incidencias is not null)", nativeQuery = true)
    List<Cuadrante> findCuadrantesConIncidencias();
}
