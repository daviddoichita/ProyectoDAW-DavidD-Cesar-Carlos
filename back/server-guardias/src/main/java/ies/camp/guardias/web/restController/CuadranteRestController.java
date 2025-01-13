package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.CuadranteDTO;
import ies.camp.guardias.service.CuadranteService;

@RestController
@RequestMapping(path = "/api/cuadrantes")
public class CuadranteRestController {

    private static final Logger log = LoggerFactory.getLogger(CuadranteRestController.class);

    @Autowired
    private CuadranteService cuadranteService;

    /**
     * Devuelve una lista con todos los CuadranteDTO en formato JSON
     * 
     * @return lista de CuadranteDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<CuadranteDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cuadrantes");

        return this.cuadranteService.findAll();
    }

    /**
     * Devuelve el CuadranteDTO asociado a la id introducida en formato JSON
     * 
     * @param id ID del Cuadrante a buscar
     * @return CuadranteDTO en formato JSON o null si la id no existe
     */
    @GetMapping(path = "/{id}")
    public CuadranteDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver cuadrante con id: {}", id);

        return this.cuadranteService.findById(id);
    }
}