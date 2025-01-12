package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.SesionDTO;

public interface SesionService {

    /**
     * Devuelve todas las sesiones como SesionDTO
     *
     * @return lista de SesionDTO
     */
    public List<SesionDTO> findAll();

    /**
     * Devuelve la SesionDTO asociada al id introducido
     *
     * @param id ID de la SesionDTO a buscar
     * @return SesionDTO si se encuentra o null en caso contrario
     */
    public SesionDTO findById(Long id);
}
