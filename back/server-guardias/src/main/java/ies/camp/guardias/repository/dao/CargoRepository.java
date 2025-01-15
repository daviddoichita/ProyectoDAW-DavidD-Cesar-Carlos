package ies.camp.guardias.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Cargo;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
