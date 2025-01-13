package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.CuadranteDTO;

public interface CuadranteService {

    /**
     * Devuelve una lista con todos los cuadrante convertidos a CuadranteDTO
     * 
     * @return lista de CuadranteDTO
     */
    public List<CuadranteDTO> findAll();

    /**
     * Devulve el CuadranteDTO asociado al Cuadrante con la id introducida
     * 
     * @param id ID del Cuandrate a buscar
     * @return CuadranteDTO si la id existe o null en caso contrario
     */
    public CuadranteDTO findById(Long id);
}
