package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.CursoDTO;
import ies.camp.guardias.service.CursoService;

@RestController
@RequestMapping(path = "/api/cursos")
public class CursoRestController {

    private static final Logger log = LoggerFactory.getLogger(CursoRestController.class);

    @Autowired
    private CursoService cursoService;

    /**
     * Devuelve todos los CursoDTO en formato JSON
     *
     * @return lista de CursoDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<CursoDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cursos");

        return this.cursoService.findAll();
    }

    /**
     * Devuelve el CursoDTO asociado a la id introducida en formato JSON o null
     *
     * @param id ID del CursoDTO a buscar
     * @return CursoDTO si existe o null en caso contrario
     */
    @GetMapping(path = "/{id}")
    public CursoDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver curso con id: {}", id);

        return this.cursoService.findById(id);
    }
}
