package ies.camp.guardias.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.dao.RolRepository;
import ies.camp.guardias.repository.entity.Profesor;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private static final Logger log = LoggerFactory.getLogger(ProfesorServiceImpl.class);

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private RolRepository rolRepository;

    /*
     * @Override
     * public List<ProfesorDTO> findAll() {
     * log.info(this.getClass().getSimpleName() +
     * " findAll: devolver todos los profesores");
     * return this.profesorRepository.findAll().stream().map(p ->
     * ProfesorDTO.convertToDTO(p))
     * .collect(Collectors.toList());
     * }
     */

    @Override
    public List<ProfesorDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");

        List<ProfesorDTO> listaProfesorDTO = new ArrayList<ProfesorDTO>();
        List<Profesor> listaProfesor = profesorRepository.findAll();
        for (int i = 0; i < listaProfesor.size(); i++) {
            Profesor profesor = listaProfesor.get(i);
            if (profesor.getActivo()) {
                ProfesorDTO profesorDTO = ProfesorDTO.convertToDTO(profesor);
                listaProfesorDTO.add(profesorDTO);
            }
        }
        return listaProfesorDTO;
    }

    /*
     * @Override
     * public ProfesorDTO findById(Long id) {
     * log.info(this.getClass().getSimpleName() +
     * " findById: devolver profesor con id: {}", id);
     * return
     * this.profesorRepository.findById(id).map(ProfesorDTO::convertToDTO).orElse(
     * null);
     * }
     */
    @Override
    public ProfesorDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver profesor con id: {}", id);

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

        Profesor profesor = profesorRepository.findById(id).get();
        profesor.setActivo(false);

        this.profesorRepository.save(profesor);
    }

    @Override
    public void save(ProfesorDTO profesorDTO, Long idProfesorBaja) {
        log.info(this.getClass().getSimpleName() + " save: guardar profesor con id: {}", profesorDTO.getId());

        String abreviacion = profesorDTO.getNombre();

        if (abreviacion != null && abreviacion.length() >= 3) {
            abreviacion = abreviacion.substring(0, 3);
        }

        Long nuevoNumero = profesorRepository.findProfesorConNumeroMayor().orElse(0L) + 1;

        Profesor profesorBaja = this.profesorRepository.findById(idProfesorBaja).get();
        profesorBaja.setActivo(false);
        this.profesorRepository.save(profesorBaja);

        Profesor profesor = new Profesor();
        profesor.setNombre(profesorDTO.getNombre());
        profesor.setApellidos(profesorDTO.getApellidos());
        profesor.setNif(profesorDTO.getNif());
        profesor.setDireccion(profesorDTO.getDireccion());
        profesor.setTelefono(profesorDTO.getTelefono());
        profesor.setEmail(profesorDTO.getEmail());
        profesor.setNumero(nuevoNumero);
        profesor.setAbreviacion(abreviacion);
        profesor.setAdmin(false);
        profesor.setActivo(true);
        profesor.setRoles(Set.of(this.rolRepository.findByNombre("profesor").get()));

        profesorRepository.save(profesor);
    }

    @Override
    public void update(ProfesorDTO profesorDTO) {
        log.info(this.getClass().getSimpleName() + " update: actualizar profesor con id: {}", profesorDTO.getId());

        Profesor profesor = ProfesorDTO.convertToEntity(profesorDTO);
        profesorRepository.save(profesor);
    }
}
