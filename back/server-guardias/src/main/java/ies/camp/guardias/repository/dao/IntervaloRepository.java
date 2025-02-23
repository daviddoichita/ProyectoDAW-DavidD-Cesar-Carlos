package ies.camp.guardias.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Intervalo;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface IntervaloRepository extends JpaRepository<Intervalo, Long> {

    @Query(value = "SELECT * FROM intervalo WHERE id = any (SELECT idintervalo FROM sesion WHERE id in (SELECT idguardia FROM cuadrante WHERE idguardia = any (SELECT id FROM sesion WHERE idprofesor = ?1)))", nativeQuery = true)
    List<Intervalo> findIntervalosGuardiasProfesor(Long idProfesor);

    @Query(value = "SELECT * FROM intervalo WHERE id = any (SELECT idintervalo FROM sesion WHERE id in (SELECT idguardia FROM cuadrante WHERE idguardia = any (SELECT id FROM sesion WHERE idprofesor = ?1 and iddia = ?2)))", nativeQuery = true)
    List<Intervalo> findIntervalosGuardiasProfesorByDia(Long idProfesor, Long idDia);

    @Query(value = "SELECT * FROM intervalo WHERE horainicio <= CURRENT_TIME() and horafin >= CURRENT_TIME()", nativeQuery = true)
    Optional<Intervalo> findNow();
}
