package ies.camp.guardias.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Aula;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AulaRepository extends JpaRepository<Aula, Long> {

	@Query(value = "SELECT * FROM aula WHERE numero = ?1", nativeQuery = true)
	public Optional<Aula> findByNumero(Long numero);
}
