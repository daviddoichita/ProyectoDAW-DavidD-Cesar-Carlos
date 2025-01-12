package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.GrupoDTO;
import ies.camp.guardias.service.GrupoService;

@RestController
@RequestMapping(path = "/api/grupos")
public class GrupoRestController {

    private static final Logger log = LoggerFactory.getLogger(GrupoRestController.class);

    @Autowired
    private GrupoService grupoService;

    /**
     * Devuelve todos los GrupoDTO en formato JSON
     *
     * @return lista de GrupoDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<GrupoDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los grupos");

        return this.grupoService.findAll();
    }

    /**
     * Devuelve el GrupoDTO asociado al id introducido
     *
     * @param id ID del GrupoDTO a buscar
     * @return GrupoDTO en formato JSON o null si no se encuentra el id introducido
     */
    @GetMapping(path = "/{id}")
    public GrupoDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver grupo con id: {}", id);

        return this.grupoService.findById(id);
    }
}
