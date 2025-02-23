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

    /**
     * Devuelve una lista con todos los intervalos donde el profesor tiene guardias
     * 
     * @param idProfesor ID del profesor a buscar sus intervalos
     * @return lista de IntervaloDTO con sus intervalos activos
     */
    public List<IntervaloDTO> findIntervalosGuardiasProfesor(Long idProfesor);

    /**
     * Devuelve una lista con todos los intervalos donde el profesor tiene guardias
     * en el dia introducido
     * 
     * @param idProfesor ID del profesor a buscar sus intervalos
     * @param idDia      ID del dia donde buscar
     * @return lista de IntervaloDTO con sus intervalos activos del dia
     */
    public List<IntervaloDTO> findIntervalosGuardiasProfesorByDia(Long idProfesor, Long idDia);

    /**
     * Devuelve el intervalo asociado a la hora actual
     * 
     * @return Intervalo si hay un intervalo asociado a la hora actual o null en
     *         caso contrario
     */
    public IntervaloDTO findNow();
}
