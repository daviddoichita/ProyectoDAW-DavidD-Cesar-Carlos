package ies.camp.guardias.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Materia;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface MateriaRepository extends JpaRepository<Materia, Long> {

	@Query(value = "SELECT * FROM materia WHERE numero = ?1", nativeQuery = true)
	public Optional<Materia> findByNumero(Long numero);
}