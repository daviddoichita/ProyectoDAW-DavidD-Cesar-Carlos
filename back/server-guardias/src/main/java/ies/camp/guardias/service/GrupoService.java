package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.GrupoDTO;

public interface GrupoService {

    /**
     * Devuelve todos los grupos de la base de datos como GrupoDTO
     *
     * @return lista de GrupoDTO
     */
    public List<GrupoDTO> findAll();

    /**
     * Devuelve el GrupoDTO asociado al id introducido
     *
     * @param id ID del Grupo a buscar
     * @return GrupoDTO si se encuentra el id o null en caso contrario
     */
    public GrupoDTO findById(Long id);
}
