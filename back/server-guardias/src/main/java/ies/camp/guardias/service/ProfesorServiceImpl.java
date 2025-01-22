package ies.camp.guardias.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.entity.Profesor;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private static final Logger log = LoggerFactory.getLogger(ProfesorServiceImpl.class);

    @Autowired
    private ProfesorRepository profesorRepository;

    /*
     * @Override public List<ProfesorDTO> findAll() {
     * log.info(this.getClass().getSimpleName() +
     * " findAll: devolver todos los profesores");
     *
     * return this.profesorRepository.findAll().stream().map(p ->
     * ProfesorDTO.convertToDTO(p)) .collect(Collectors.toList()); }
     */

    @Override
    public List<ProfesorDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");

        List<ProfesorDTO> listaProfesorDTO = new ArrayList<ProfesorDTO>();
        List<Profesor> listaProfesor = profesorRepository.findAll();
        for (int i = 0; i < listaProfesor.size(); i++) {
            Profesor profesor = listaProfesor.get(i);
            ProfesorDTO profesorDTO = ProfesorDTO.convertToDTO(profesor);
            listaProfesorDTO.add(profesorDTO);
        }
        return listaProfesorDTO;
    }

    /*
     * @Override public ProfesorDTO findById(Long id) {
     * log.info(this.getClass().getSimpleName() +
     * " findById: devolver profesor con id: {}", id);
     *
     * return
     * this.profesorRepository.findById(id).map(ProfesorDTO::convertToDTO).orElse(
     * null); }
     */

    @Override
    public ProfesorDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver todos los profesores");

        Optional<Profesor> profesor = profesorRepository.findById(id);
        if (profesor.isPresent()) {
            ProfesorDTO profesorDTO = ProfesorDTO.convertToDTO(profesor.get());
            return profesorDTO;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        log.info(this.getClass().getSimpleName() + " delete: borrar profesor con id: {}", id);

        profesorRepository.deleteById(id);
    }

    @Override
    public void save(ProfesorDTO profesorDTO) {
        log.info(this.getClass().getSimpleName() + " save: guardar profesor con id: {}", profesorDTO.getId());
        // Cifrar la contraseña antes de guardar
        Profesor profesor = ProfesorDTO.convertToEntity(profesorDTO);
        profesorRepository.save(profesor);
    }
}
