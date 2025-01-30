package ies.camp.guardias.web.restController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.service.DiaService;

@RestController
@RequestMapping(path = "/api/dias")
public class DiaRestController {

    private static final Logger log = LoggerFactory.getLogger(DiaRestController.class);

    @Autowired
    private DiaService diaService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los dias");

        Map<String, Object> response = Map.of("data", this.diaService.findAll(), "status", "ok");

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver dia con id: {}", id);

        Map<String, Object> response = Map.of("data", this.diaService.findById(id), "status", "ok");

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/abreviacion")
    public ResponseEntity<Map<String, Object>> findByAbreviacion(@RequestParam String abreviacion) {
        log.info(this.getClass().getSimpleName() + " findByAbreviacion devolver dia con abreviacion: {}", abreviacion);

        Map<String, Object> response = Map.of("data", this.diaService.findByAbreviacion(abreviacion), "status", "ok");

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/abreviaciones")
    public ResponseEntity<Map<String, Object>> findByAbreviaciones(@RequestParam List<String> abreviaciones) {
        log.info(this.getClass().getSimpleName() + " findByAbreviaciones: devolver dias con abreviaciones",
                Arrays.toString(abreviaciones.toArray()));

        Map<String, Object> response = Map.of("data", this.diaService.findByAbreviaciones(abreviaciones), "status",
                "ok");

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/currentWeek")
    public ResponseEntity<Map<String, Object>> findCurrentWeek() {
        log.info(this.getClass().getSimpleName() + " findCurrentWeek: devolver los dias de hoy al viernes");

        Map<String, Object> response = Map.of("data", this.diaService.findCurrentWeek(), "status", "ok");

        return ResponseEntity.ok(response);
    }
}
