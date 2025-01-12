package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.SesionDTO;
import ies.camp.guardias.service.SesionService;

@RestController
@RequestMapping(path = "/api/sesiones")
public class SesionRestController {

    private static final Logger log = LoggerFactory.getLogger(SesionRestController.class);

    @Autowired
    private SesionService sesionService;

    /**
     * Devuelve todas las SesionDTO en formato JSON
     *
     * @return lista SesionDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<SesionDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todas las sesiones");

        return this.sesionService.findAll();
    }

    /**
     * Devuelve la SesionDTO asociada al id introducido en formato JSON
     *
     * @param id ID de la SesionDTO a buscar
     * @return SesionDTO en formato JSON si se encuentra o null en caso contrario
     */
    @GetMapping(path = "/{id}")
    public SesionDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver sesion con id: {}", id);

        return this.sesionService.findById(id);
    }
}
