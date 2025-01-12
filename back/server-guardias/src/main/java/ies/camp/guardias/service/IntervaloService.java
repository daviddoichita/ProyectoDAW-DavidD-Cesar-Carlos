package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.IntervaloDTO;

public interface IntervaloService {

    /**
     * Devuelve una lista con todos los intervalos convertidos a IntervaloDTO
     *
     * @return lista de IntervaloDTO
     */
    public List<IntervaloDTO> findAll();

    /**
     * Devuelve el IntervaloDTO asociado al id introducido
     *
     * @param id ID del IntervaloDTO a buscar
     * @return IntervaloDTO si se encuentra o null en caso contrario
     */
    public IntervaloDTO findById(Long id);
}
