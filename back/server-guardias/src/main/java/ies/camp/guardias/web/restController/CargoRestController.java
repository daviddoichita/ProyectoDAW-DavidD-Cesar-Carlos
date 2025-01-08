package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.CargoDTO;
import ies.camp.guardias.service.CargoService;

@RestController
@RequestMapping(path = "/api/cargos")
public class CargoRestController {

    private static final Logger log = LoggerFactory.getLogger(CargoRestController.class);

    @Autowired
    private CargoService cargoService;

    /**
     * Devuelve todos los CargoDTO en formato JSON
     *
     * @return lista de CargoDTO en formato JSON
     */
    @GetMapping(path = "")
    public List<CargoDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cargos");

        return this.cargoService.findAll();
    }

    /**
     * Devuelve el CargoDTO asociado al id introducido en formato JSON
     *
     * @param id ID del CargoDTO a buscar
     * @return CargoDTO en formato JSON si existe o null en caso contrario
     */
    @GetMapping(path = "/{id}")
    public CargoDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver cargo con id: {}", id);

        return this.cargoService.findById(id);
    }
}
