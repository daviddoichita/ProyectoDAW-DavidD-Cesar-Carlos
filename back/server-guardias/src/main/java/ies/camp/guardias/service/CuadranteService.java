package ies.camp.guardias.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

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

    /**
     * Devuelve una lista de cuadrantes de la semana actual
     *
     * @return lista de CuadranteDTO de la semana actual
     */
    public List<CuadranteDTO> findCurrentWeek();

    /**
     * Devuelve una lista de cuadrantes del dia actual
     *
     * @return lista de CuadranteDTO del dia actual
     */
    public List<CuadranteDTO> findToday();

    /**
     * Firma un cuadrante y devuelve el estado de la actualizacion
     * 
     * @param firma Firma con la que actualizar el cuadrante
     * 
     * @return 0 ok
     * @return 1 error en actualizacion
     * @return 2 error de autorizacion
     * @return 3 error de intervalo de tiempo
     */
    public int firmarCuadrante(UserDetails currentUser, Long id, Long idFalta, String firma);

    /**
     * Edita un cuadrante para asignarle una incidencia
     * 
     * @param incidencia incidencia con la que actualizar el cuadrante
     * 
     * @return 0 ok
     * @return 1 error en actualizacion
     * @return 2 error de autorizacion
     * @return 3 error de intervalo de tiempo
     */
    public int addIncidenciaCuadrante(UserDetails currentUser, Long id, Long idFalta, String incidencia);

    /**
     * Devuelve una lista con todos los cuadrantes que tengan faltas
     *
     * @return lista de Cuadrantes con faltas
     */
    public List<CuadranteDTO> findCuadranteConFaltas();

    /**
     * Devuelve una lista con todos los cuadrantes que tengan sesiones/guardias sin
     * firmar
     * (Solo hasta 2026 para no cargar todos los cuadrantes, ya que todos tienen la
     * firma nula)
     * 
     * @return lista de Cuadrantes con sesiones sin firmar
     */
    public List<CuadranteDTO> findCuadranteSinFirmar();

    /**
     * Devuelve una lista con todos los cuadrantes que tengan sesiones con alguna
     * incidencia
     *
     * @return lista de Cuadrantes que tengan incidencias en sesiones
     */
    public List<CuadranteDTO> findCuadranteConIncidencias();

}
