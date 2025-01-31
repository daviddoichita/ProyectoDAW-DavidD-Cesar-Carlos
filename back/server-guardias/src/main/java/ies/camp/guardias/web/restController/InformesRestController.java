package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ies.camp.guardias.model.dto.CuadranteDTO;
import ies.camp.guardias.service.CuadranteService;

@RestController
@RequestMapping(path = "/api/informes")
public class InformesRestController {
    private static final Logger log = LoggerFactory.getLogger(InformesRestController.class);

    @Autowired
    private CuadranteService cuadranteService;

    @GetMapping("/faltas")
    public List<CuadranteDTO> findCuadrantesConFalta() {
        log.info(
                this.getClass().getSimpleName()
                        + " findCuadrantesConFaltas: Devolver cuadrantes que tienen faltas");
        return cuadranteService.findCuadranteConFalta();
    }

    @GetMapping("/asistencias")
    public List<CuadranteDTO> findCuadrantesSinFirmar() {
        log.info(this.getClass().getSimpleName()
                + " findCuadrantesSinAsistir: Devolver cuadrantes sin firmar");
        return cuadranteService.findCuadranteSinFirmar();
    }

    @GetMapping("/incidencias")
    public List<CuadranteDTO> findCuadrantesConIncidencia() {
        log.info(this.getClass().getSimpleName()
                + " findCuadrantesConIncidencia: Devolver cuadrantes que tienen incidencias");
        return cuadranteService.findCuadranteConIncidencia();
    }
}