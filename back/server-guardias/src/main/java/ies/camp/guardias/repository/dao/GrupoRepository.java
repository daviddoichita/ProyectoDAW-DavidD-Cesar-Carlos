package ies.camp.guardias.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Grupo;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
