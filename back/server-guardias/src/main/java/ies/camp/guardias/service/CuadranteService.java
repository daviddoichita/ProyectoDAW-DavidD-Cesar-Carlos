package ies.camp.guardias.service;

import java.time.LocalDate;
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

    /**
     * Devuelve los Cuadrantes de un determinad rango
     * 
     * @param start primer dia
     * @param end   ultimo dia
     * @return lista con los CuadranteDTO del rango introducido
     */
    public List<CuadranteDTO> findByRange(LocalDate start, LocalDate end);

    /**
     * Genera los Cuadrantes a partir de las Sesiones de la base de datos
     * Elimina todos los cuadrantes previamente generados
     * 
     * @param asignarRoles especifica si asignar los roles en los cuadrantes despues
     *                     de la generacion
     * @return exito de la generacion
     */
    public boolean generate(boolean asignarRoles);

    /**
     * Genera los LocalDateTime de las fechas del 11 de septiembre al 22 de junio
     * 
     * @return
     */
    public List<LocalDate> getFechasCuso();
}
