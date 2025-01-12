package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.CargoDTO;

public interface CargoService {

    /**
     * Devuelve todos los cargos como CargoDTO
     *
     * @return lista de CargoDTO
     */
    public List<CargoDTO> findAll();

    /**
     * Devuelve el CargoDTO asociado al id introducido
     *
     * @param id ID del CargoDTO a buscar
     * @return CargoDTO si existe o null en caso contrario
     */
    public CargoDTO findById(Long id);
}
