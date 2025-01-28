package ies.camp.guardias.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Profesor;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    @Query("SELECT p FROM Profesor p WHERE p.email = ?1")
    Optional<Profesor> findByEmail(String email);

    @Query("SELECT p FROM Profesor p WHERE p.nif = ?1")
    Optional<Profesor> findByNif(String nif);
  
	  @Query(value = "SELECT * FROM profesor WHERE numero = ?1", nativeQuery = true)
	  public Optional<Profesor> findByNumero(Long numero);
}