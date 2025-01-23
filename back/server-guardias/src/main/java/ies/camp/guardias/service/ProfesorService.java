package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.ProfesorDTO;

public interface ProfesorService {

    /**
     * Devuelve todos los profesores de la base de datos convertidos a ProfesorDTO
     *
     * @return lista de ProfesorDTO
     */
    // public List<ProfesorDTO> findAll();

    public List<ProfesorDTO> findAll();

    /**
     * Devuelve el ProfesorDTO asociado al id introducido o null si no existe
     *
     * @param id ID del Profesor a buscar
     * @return Profesor si encuentra el id introducido o null en caso contrario
     */
    public ProfesorDTO findById(Long id);

    /**
     * Borra el Profesor asociado al id introducido
     *
     * @param id ID del Profesor a borrar
     */
    public void delete(Long id);

    /**
     * Guarda el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a guardar
     */
    public void save(ProfesorDTO profesorDTO);
}
