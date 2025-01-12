package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.MateriaDTO;
import ies.camp.guardias.service.MateriaService;

@RestController
@RequestMapping(path = "/api/materias")
public class MateriaRestController {

    private static final Logger log = LoggerFactory.getLogger(MateriaRestController.class);

    @Autowired
    private MateriaService materiaService;

    /**
     * Devuelve todos las MateriaDTO en formato JSON
     *
     * @return lista de MateriaDTO
     */
    @GetMapping(path = "")
    public List<MateriaDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todas las materias");

        return this.materiaService.findAll();
    }

    /**
     * Devuelve la MateriaDTO asociada al id introducido en formato JSON
     *
     * @param id ID de la MateriaDTO a buscar
     * @return MateriaDTO en formato JSON o null si no se encuentra el id
     *         introducido
     */
    @GetMapping(path = "/{id}")
    public MateriaDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " devolver materia con id: {}", id);

        return this.materiaService.findById(id);
    }
}
