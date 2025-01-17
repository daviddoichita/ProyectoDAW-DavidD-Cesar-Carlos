package ies.camp.guardias.web.restController;

import java.awt.Container;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.service.ProfesorService;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorRestController {

    private static final Logger log = LoggerFactory.getLogger(ProfesorRestController.class);

    @Autowired
    private ProfesorService profesorService;

    /**
     * Devuelve todos los ProfesorDTO en formato JSON
     *
     * @return lista de ProfesorDTO
     */
    @GetMapping(path = "")
    public List<ProfesorDTO> findAll(@AuthenticationPrincipal User user) {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");
        log.info(this.getClass().getSimpleName() + "Usuario logeado:" +user);

        return this.profesorService.findAll();
    }

    /**
     * Devuelve el ProfesorDTO con id introducido en formato JSON
     *
     * @param id ID del ProfesorDTO a buscar
     * @return ProfesorDTO en formato JSON o null si no se encuentra la id
     *         introducida
     */
    @GetMapping(path = "/{id}")
    public ProfesorDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver profesor con id: {}", id);

        return this.profesorService.findById(id);
    }

    /**
     * Borra el Profesor con id introducido
     *
     * @param id ID del Profesor a borrar
     */
    @GetMapping(path = "/{id}/delete")
    public void delete(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " deleteById: borrar profesor con id: {}", id);

        this.profesorService.delete(id);
    }

    /**
     * Guarda el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a guardar
     */
    @GetMapping(path = "/{id}/save")
    public void save(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " save: guardar profesor con id: {}", id);

        this.profesorService.save(this.profesorService.findById(id));
    }
    
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String contrasenya) {
        log.info("Intentando iniciar sesión para el email: {}", email);
        try {
            String usuarioId = profesorService.login(email, contrasenya);
            log.info("Inicio de sesión exitoso para el usuario con ID: {}", usuarioId);
            return ResponseEntity.ok(usuarioId);
        } catch (Exception e) {
            log.error("Error en el inicio de sesión para el email: {}. Causa: {}", email, e.getMessage());
            return ResponseEntity.status(401).body("error");
        }
    }

}