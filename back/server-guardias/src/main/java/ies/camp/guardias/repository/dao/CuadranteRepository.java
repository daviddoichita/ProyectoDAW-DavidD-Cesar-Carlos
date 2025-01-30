package ies.camp.guardias.repository.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Cuadrante;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CuadranteRepository extends JpaRepository<Cuadrante, Long> {

    @Query(value = "SELECT * from cuadrante where id = any (select idcuadrante from sesionfalta)", nativeQuery = true)
    List<Cuadrante> findCuadrantesConFaltas();

    // Solo hasta 2026 para no cargar todos los cuadrantes
    // (todos tienen la firma nula)
    @Query(value = "SELECT * from cuadrante where firma is null and fecha < '2026-01-01'", nativeQuery = true)
    List<Cuadrante> findCuadrantesSinFirmar();

    @Query(value = "SELECT * from cuadrante where incidencias is not null", nativeQuery = true)
    List<Cuadrante> findCuadrantesConIncidencia();

}
