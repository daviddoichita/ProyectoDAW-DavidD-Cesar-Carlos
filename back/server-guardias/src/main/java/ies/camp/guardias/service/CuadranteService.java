package ies.camp.guardias.service;

import java.time.LocalDate;
import java.util.List;

import ies.camp.guardias.model.dto.CuadranteDTO;

public interface CuadranteService {

    /**
     * Devuelve una lista con todos los cuadrantes como DTO
     *
     * @return lista de CuadranteDTO
     */
    public List<CuadranteDTO> findAll();

    /**
     * Devuelve el CuadranteDTO asociado al id introducido
     *
     * @param id id del cuadrante a buscar
     * @return CuadranteDTO o null si no se encuentra
     */
    public CuadranteDTO findById(Long id);

    /**
     * Devuelve una lista de cuadrantes que esten en el rango de fechas introducido
     *
     * @param start fecha inicio
     * @param end   fecha fin
     * @return lista de CuadranteDTO dentro del rango introducido
     */
    public List<CuadranteDTO> findByRange(LocalDate start, LocalDate end);
}
