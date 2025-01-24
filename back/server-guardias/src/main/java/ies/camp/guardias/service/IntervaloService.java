package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.IntervaloDTO;

public interface IntervaloService {

    /**
     * Devuelve una lista de todos los intervalos como DTO
     *
     * @return lista de intervalos DTO
     */
    public List<IntervaloDTO> findAll();
}
