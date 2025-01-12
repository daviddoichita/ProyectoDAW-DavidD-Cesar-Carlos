package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
