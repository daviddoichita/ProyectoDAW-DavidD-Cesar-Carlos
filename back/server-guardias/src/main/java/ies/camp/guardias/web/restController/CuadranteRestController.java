package ies.camp.guardias.web.restController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /**
     * Devuelve los CuadranteDTO en el rango introducido
     * 
     * @return lista de CuadranteDTO en el rango introducido
     */
    @PostMapping(path = "/range")
    public List<CuadranteDTO> findByRange(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        log.info(this.getClass().getSimpleName() + " findByRange: devolver cuadrantes en el rango: [{}, {}]", start,
                end);

        return this.cuadranteService.findByRange(start, end);
    }

    /**
     * Manda la peticion de generar cuadrantes al service
     * 
     * @param roles define si asignar roles al finalizar la generacion
     * @return exito de la generacion
     */
    @GetMapping(path = "/generate/{roles}")
    @ResponseBody
    public Map<String, Boolean> generate(@PathVariable Boolean roles) {
        log.info(this.getClass().getSimpleName() + " generate: generar cuadrantes");

        Map<String, Boolean> response = new HashMap<>();
        response.put("exito", this.cuadranteService.generate(roles));
        return response;
    }
}