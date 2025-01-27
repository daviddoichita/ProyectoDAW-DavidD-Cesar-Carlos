package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.DiaDTO;

public interface DiaService {

    /**
     * Devuelve una lista de todos los dias como DTO
     *
     * @return lista de DiaDTO
     */
    public List<DiaDTO> findAll();

    /**
     * Devuelve el DiaDTO asociado a la id introducida
     * 
     * @param id ID del dia a buscar
     * @return DiaDTO si encuentra la id o null en caso contrario
     */
    public DiaDTO findById(Long id);

    /**
     * Devuelve el DiaDTO con la abreviacion introducida
     *
     * @param abreviacion abreviacion del dia a buscar
     * @return DiaDTO si se encuentra o null en caso contrario
     */
    public DiaDTO findByAbreviacion(String abreviacion);

    /**
     * Devuelve una lista de DiaDTO acordes con las abreviaciones introducidas
     *
     * @param abreviaciones abreviaciones de dia a buscar
     * @return lista de DiaDTO con los dias encontrados o null en caso contrario
     */
    public List<DiaDTO> findByAbreviaciones(List<String> abreviaciones);

    /**
     * Devuelve una lista de los dias de hoy al viernes
     *
     * @return lista de DiaDTO
     */
    public List<DiaDTO> findCurrentWeek();
}
