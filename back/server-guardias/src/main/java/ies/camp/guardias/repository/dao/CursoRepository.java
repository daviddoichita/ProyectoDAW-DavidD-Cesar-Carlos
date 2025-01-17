package ies.camp.guardias.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Curso;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Query(value = "SELECT * FROM curso WHERE abreviacion = '24/25'", nativeQuery = true)
	public Optional<Curso> getCurrent();
}
