package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.service.ProfesorService;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorRestController {

    private static final Logger log = LoggerFactory.getLogger(ProfesorRestController.class);

    @Autowired
    private ProfesorService profesorService;

    /**
     * Devuelve todos los ProfesorDTO en formato JSON
     *
     * @return lista de ProfesorDTO
     */
    @GetMapping(path = "")
    public List<ProfesorDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");

        return this.profesorService.findAll();
    }

    /**
     * Devuelve el ProfesorDTO con id introducido en formato JSON
     *
     * @param id ID del ProfesorDTO a buscar
     * @return ProfesorDTO en formato JSON o null si no se encuentra la id
     *         introducida
     */
    @GetMapping(path = "/{id}")
    public ProfesorDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver profesor con id: {}", id);

        return this.profesorService.findById(id);
    }

    /**
     * Borra el Profesor con id introducido
     *
     * @param id ID del Profesor a borrar
     */
    @DeleteMapping(path = "/{id}/delete")
    public void delete(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " deleteById: borrar profesor con id: {}", id);

        this.profesorService.delete(id);
    }

    /**
     * Guarda el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a guardar
     */
    @GetMapping(path = "/{id}/save")
    public void save(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " save: guardar profesor con id: {}", id);

        this.profesorService.save(this.profesorService.findById(id));
    }

    /**
     * Actualiza el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a actualizar
     */
    @PutMapping(path = "/{id}/update")
    public void update(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " update: actualizar profesor con id: {}", id);

        this.profesorService.update(this.profesorService.findById(id));
    }
}