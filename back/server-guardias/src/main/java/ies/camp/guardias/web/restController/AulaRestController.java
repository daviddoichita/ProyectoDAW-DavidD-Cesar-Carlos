package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.AulaDTO;
import ies.camp.guardias.service.AulaService;

@RestController
@RequestMapping(path = "/api/aulas")
public class AulaRestController {

    private static final Logger log = LoggerFactory.getLogger(AulaRestController.class);

    @Autowired
    private AulaService aulaService;

    /**
     * Devuelve todas las Aulas como AulaDTO en formato JSON
     * 
     * @return lista de AulaDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<AulaDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todas las aulas");

        return this.aulaService.findAll();
    }

    /**
     * Devuelve el aula buscada en formato JSON
     * 
     * @param id ID del Aula a buscar
     * @return AulaDTO en formato JSON
     */
    @GetMapping(path = "/{id}")
    public AulaDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver aula con id: {}", id);

        return this.aulaService.findById(id);
    }
}
