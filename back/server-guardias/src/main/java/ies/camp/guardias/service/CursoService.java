package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.CursoDTO;

public interface CursoService {

    /**
     * Devuelve todos los cursos convertidos a CursoDTO
     *
     * @return lista de CursoDTO
     */
    public List<CursoDTO> findAll();

    /**
     * Devuelve el CursoDTO asociado al id introducido
     *
     * @param id ID del CursoDTO a buscar
     * @return CursoDTO si existe o null en caso contrario
     */
    public CursoDTO findById(Long id);
}
