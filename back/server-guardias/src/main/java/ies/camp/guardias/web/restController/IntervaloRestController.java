package ies.camp.guardias.web.restController;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los intervalos");

        Map<String, Object> response = Map.of("data", this.intervaloService.findAll(), "status", "ok");

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/profesor/{id}")
    public ResponseEntity<List<IntervaloDTO>> findIntervalosGuardiasProfesor(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName()
                + " findIntervalosGuardiasProfesor: devolver los intervalos de guardia del profesor: {}", id);

        return ResponseEntity.ok(this.intervaloService.findIntervalosGuardiasProfesor(id));
    }

    @GetMapping(path = "/profesor/{idProfesor}/dia/{idDia}")
    public ResponseEntity<List<IntervaloDTO>> findIntervalosGuardiasProfesorByDia(@PathVariable Long idProfesor,
            @PathVariable Long idDia) {
        log.info(this.getClass().getSimpleName()
                + " findIntervalosGuardiasProfesorByDia: devolver los intervalos de guardia del profesor: {} del dia: {}",
                idProfesor, idDia);

        return ResponseEntity.ok(this.intervaloService.findIntervalosGuardiasProfesorByDia(idProfesor, idDia));
    }
}
