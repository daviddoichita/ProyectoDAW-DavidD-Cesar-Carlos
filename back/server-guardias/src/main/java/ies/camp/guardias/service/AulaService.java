package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.AulaDTO;

public interface AulaService {

    /**
     * Devuelve una lista de todas las aulas como AulaDTO
     * 
     * @return lista de AulaDTO
     */
    public List<AulaDTO> findAll();

    /**
     * Devulve el AulaDTO del Aula asociada al id introducido
     * 
     * @param id ID del Aula a buscar
     * @return AulaDTO si el ID existe o null en caso contrario
     */
    public AulaDTO findById(Long id);
}
