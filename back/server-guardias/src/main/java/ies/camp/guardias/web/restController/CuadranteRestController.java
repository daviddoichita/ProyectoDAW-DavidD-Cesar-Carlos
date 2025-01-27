package ies.camp.guardias.web.restController;

import java.rmi.server.ObjID;
import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.service.CuadranteService;

@RestController
@RequestMapping(path = "/api/cuadrantes")
public class CuadranteRestController {

    private static final Logger log = LoggerFactory.getLogger(CuadranteRestController.class);

    @Autowired
    private CuadranteService cuadranteService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cuadrantes");

        Map<String, Object> response = Map.of("data", this.cuadranteService.findAll(), "status", "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver cuadrante con id: {}", id);

        Map<String, Object> response = Map.of("data", this.cuadranteService.findById(id), "status", "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/range/{start}/{end}")
    public ResponseEntity<Map<String, Object>> findByRange(@PathVariable LocalDate start, @PathVariable LocalDate end) {
        log.info(
                this.getClass().getSimpleName()
                        + " findByRange: devolver los cuadrantes dentro de las fechas [ {} : {} ]",
                start, end);

        Map<String, Object> response = Map.of("data", this.cuadranteService.findByRange(start, end), "status", "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/currentWeek")
    public ResponseEntity<Map<String, Object>> findCurrentWeek() {
        log.info(this.getClass().getSimpleName() + " findCurrentWeek: devolver la semana actual");

        Map<String, Object> response = Map.of("data", this.cuadranteService.findCurrentWeek(), "status", "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/today")
    public ResponseEntity<Map<String, Object>> findToday() {
        log.info(this.getClass().getSimpleName() + " findToday: devolver cuadrantes del dia actual");

        Map<String, Object> response = Map.of("data", this.cuadranteService.findToday(), "status", "ok");
        return ResponseEntity.ok().body(response);
    }
}
