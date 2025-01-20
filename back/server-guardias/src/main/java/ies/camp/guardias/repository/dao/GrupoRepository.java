package ies.camp.guardias.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Grupo;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	@Query(value = "SELECT * FROM grupo WHERE numero = ?1", nativeQuery = true)
	public Optional<Grupo> findByNumero(Long numero);
}
