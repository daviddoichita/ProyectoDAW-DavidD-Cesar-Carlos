package ies.camp.guardias.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ies.camp.guardias.repository.entity.Falta;

@Repository
@Transactional
public interface FaltaRepository extends JpaRepository<Falta, Long> {

}
