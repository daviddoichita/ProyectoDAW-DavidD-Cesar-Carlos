package ies.camp.guardias.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Materia;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface MateriaRepository extends JpaRepository<Materia, Long> {

}
