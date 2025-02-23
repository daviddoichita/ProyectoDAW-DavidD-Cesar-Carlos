package ies.camp.guardias.web.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.FaltaDTO;
import ies.camp.guardias.service.FaltaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/faltas")
@RequiredArgsConstructor
public class FaltaRestController {

    @Autowired
    private FaltaService faltaService;

    @GetMapping
    public ResponseEntity<List<FaltaDTO>> obtenerTodasLasFaltas() {
        return ResponseEntity.ok(faltaService.obtenerTodasLasFaltas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaltaDTO> obtenerFaltaPorId(@PathVariable Long id) {
        FaltaDTO faltaDTO = faltaService.obtenerFaltaPorId(id);
        return faltaDTO != null ? ResponseEntity.ok(faltaDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FaltaDTO> registrarFalta(@RequestBody FaltaDTO faltaDTO) {
        return ResponseEntity.ok(faltaService.registrarFalta(faltaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFalta(@PathVariable Long id) {
        faltaService.eliminarFalta(id);
        return ResponseEntity.noContent().build();
    }
}
