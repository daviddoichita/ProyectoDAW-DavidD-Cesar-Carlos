package ies.camp.guardias.web.restController;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.model.dto.SesionDTO;
import ies.camp.guardias.service.SesionService;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/sesiones")
@PreAuthorize("hasRole('DIRECCION')")
public class SesionRestController {

    private static final Logger log = LoggerFactory.getLogger(
            SesionRestController.class);

    @Autowired
    private SesionService sesionService;

    /**
     * Recibe el archivo CSV y lo manda al service para cargar la base de datos a
     * traves de el
     *
     * @param file Archivo CSV
     * @return true si la carga fue exitosa false en caso contrario
     */
    @PostMapping(path = "/load")
    public ResponseEntity<Map<String, String>> loadFromCSV(
            @RequestParam MultipartFile file,
            @RequestParam int year) {
        log.info(
                this.getClass().getSimpleName() +
                        " loadFromCSV: mandar archivo CSV a SesionService");

        LocalTime start = LocalTime.now();
        Boolean result = this.sesionService.loadFromCSV(file, year);
        LocalTime end = LocalTime.now();

        return ResponseEntity.ok(
                Map.of(
                        "ok",
                        result.toString(),
                        "took",
                        String.valueOf(end.toSecondOfDay() - start.toSecondOfDay()) +
                                "s"));
    }

    @GetMapping(path = "/profesor/{id}")
    public List<SesionDTO> findSesionesPorProfesor(@PathVariable Long id) {

        log.info(this.getClass().getSimpleName() + " findSesionesPorProfesor: mostramos las sesiones por profesor");

        ProfesorDTO profesorDTO = new ProfesorDTO();
        profesorDTO.setId(id);
        List<SesionDTO> listaSesionesDTO = sesionService.findAllBySesiones(profesorDTO);

        return listaSesionesDTO;
    }
}