package ies.camp.guardias.web.restController;

import ies.camp.guardias.service.CuadranteService;
import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/cuadrantes")
public class CuadranteRestController {

    private static final Logger log = LoggerFactory.getLogger(
            CuadranteRestController.class);

    @Autowired
    private CuadranteService cuadranteService;

    @GetMapping(path = "")
    @PreAuthorize("hasRole('DIRECCION')")
    public ResponseEntity<Map<String, Object>> findAll() {
        log.info(
                this.getClass().getSimpleName() +
                        " findAll: devolver todos los cuadrantes");

        Map<String, Object> response = Map.of(
                "data",
                this.cuadranteService.findAll(),
                "status",
                "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('DIRECCION')")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        log.info(
                this.getClass().getSimpleName() +
                        " findById: devolver cuadrante con id: {}",
                id);

        Map<String, Object> response = Map.of(
                "data",
                this.cuadranteService.findById(id),
                "status",
                "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/range/{start}/{end}")
    public ResponseEntity<Map<String, Object>> findByRange(
            @PathVariable LocalDate start,
            @PathVariable LocalDate end) {
        log.info(
                this.getClass().getSimpleName() +
                        " findByRange: devolver los cuadrantes dentro de las fechas [ {} : {} ]",
                start,
                end);

        Map<String, Object> response = Map.of(
                "data",
                this.cuadranteService.findByRange(start, end),
                "status",
                "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/currentWeek")
    public ResponseEntity<Map<String, Object>> findCurrentWeek() {
        log.info(
                this.getClass().getSimpleName() +
                        " findCurrentWeek: devolver la semana actual");

        Map<String, Object> response = Map.of(
                "data",
                this.cuadranteService.findCurrentWeek(),
                "status",
                "ok");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/today")
    @PreAuthorize("hasRole('DIRECCION')")
    public ResponseEntity<Map<String, Object>> findToday() {
        log.info(
                this.getClass().getSimpleName() +
                        " findToday: devolver cuadrantes del dia actual");

        Map<String, Object> response = Map.of(
                "data",
                this.cuadranteService.findToday(),
                "status",
                "ok");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/firmar")
    public ResponseEntity<Integer> firmarCuadrante(@RequestParam Long idCuadrante, @RequestParam Long idFalta,
            @RequestParam String firma) {
        log.info(this.getClass().getSimpleName() + " firmarCuadrante: firmar cuadrante con id: {}", idCuadrante);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

        Integer status = this.cuadranteService.firmarCuadrante(currentUser, idCuadrante, idFalta, firma);

        if (status == 0) {
            return ResponseEntity.ok(status);
        } else if (status == 2) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
