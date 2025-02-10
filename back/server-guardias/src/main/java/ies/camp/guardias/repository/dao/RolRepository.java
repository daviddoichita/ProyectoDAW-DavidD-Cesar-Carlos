package ies.camp.guardias.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ies.camp.guardias.repository.entity.Rol;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query(value = "SELECT * FROM roles WHERE nombre = ?1", nativeQuery = true)
    public Optional<Rol> findByNombre(String nombre);
}
