package ies.camp.guardias.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Profesor;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByEmail(String email);
}