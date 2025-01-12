package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.IntervaloDTO;
import ies.camp.guardias.service.IntervaloService;

@RestController
@RequestMapping(path = "/api/intervalos")
public class IntervaloRestController {

    private static final Logger log = LoggerFactory.getLogger(IntervaloRestController.class);

    @Autowired
    private IntervaloService intervaloService;

    /**
     * Devuelve una lista con todos los intervalosDTO en formato JSON
     *
     * @return lista de IntervaloDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<IntervaloDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los intervalos");

        return this.intervaloService.findAll();
    }

    /**
     * Devuelve el IntervaloDTO asociado al id introducido
     *
     * @param id ID del IntervaloDTO a buscar
     * @return IntervaloDTO en formato JSON si existe o null en caso contrario
     */
    @GetMapping(path = "/{id}")
    public IntervaloDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver intervalo con id: {}", id);

        return this.intervaloService.findById(id);
    }
}
