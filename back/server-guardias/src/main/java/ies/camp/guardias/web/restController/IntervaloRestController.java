package ies.camp.guardias.web.restController;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
