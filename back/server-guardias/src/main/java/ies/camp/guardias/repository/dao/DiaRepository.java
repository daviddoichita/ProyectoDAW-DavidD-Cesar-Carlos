package ies.camp.guardias.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Dia;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface DiaRepository extends JpaRepository<Dia, Long> {

    @Query(value = "SELECT * FROM dia WHERE abreviacion = ?1", nativeQuery = true)
    public Optional<Dia> findByAbreviacion(String abreviacion);

    @Query(value = "SELECT * FROM dia WHERE abreviacion IN (:abreviaciones)", nativeQuery = true)
    public List<Dia> findByAbreviaciones(List<String> abreviaciones);
}
