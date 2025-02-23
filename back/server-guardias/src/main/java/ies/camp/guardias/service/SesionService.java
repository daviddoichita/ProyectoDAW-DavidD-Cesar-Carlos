package ies.camp.guardias.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.model.dto.SesionDTO;

public interface SesionService {

    /**
     * Lee todas las sesiones desde un archivo .CSV y carga en la base de datos toda
     * la informacion de todas las entidades
     *
     * @param csv Archivo CSV con la informacion de la base de datos
     * @return true si la carga ha sido exitosa o false en caso contrario
     */
    public boolean loadFromCSV(MultipartFile csv, int year);

    public List<SesionDTO> findAllBySesiones(ProfesorDTO profesorDTO);
}
