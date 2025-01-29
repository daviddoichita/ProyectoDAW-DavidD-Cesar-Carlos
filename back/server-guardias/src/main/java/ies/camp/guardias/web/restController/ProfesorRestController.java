package ies.camp.guardias.web.restController;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.service.JwtService;
import ies.camp.guardias.service.ProfesorService;

@RestController
public class ProfesorRestController {

    private static final Logger log = LoggerFactory.getLogger(ProfesorRestController.class);

    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private JwtService jwtService;

    @GetMapping(path = "/api/me")
    public ResponseEntity<Map<String, Object>> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

        String token = this.jwtService.generateToken(currentUser);

        Map<String, Object> response = Map.of("user", currentUser, "token", token);

        log.info(this.getClass().getSimpleName() + " me: devolver usuario logeado");
        return ResponseEntity.ok(response);
    }

    /**
     * Devuelve todos los ProfesorDTO en formato JSON
     *
     * @return lista de ProfesorDTO
     */
    @GetMapping(path = "/api/profesores")
    public List<ProfesorDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");

        return this.profesorService.findAll();
    }

    /**
     * Devuelve el ProfesorDTO con id introducido en formato JSON
     *
     * @param id ID del ProfesorDTO a buscar
     * @return ProfesorDTO en formato JSON o null si no se encuentra la id
     *         introducida
     */
    @GetMapping(path = "/api/profesores/{id}")
    public ProfesorDTO findById(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver profesor con id: {}", id);

        return this.profesorService.findById(id);
    }

    /**
     * Borra el Profesor con id introducido
     *
     * @param id ID del Profesor a borrar
     */
    @DeleteMapping(path = "/{id}/delete")
    public void delete(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " deleteById: borrar profesor con id: {}", id);

        this.profesorService.delete(id);
    }

    /**
     * Guarda el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a guardar
     */
    @PostMapping(path = "/save")
    public void save(@RequestParam ProfesorDTO profesorDTO) {
        log.info(this.getClass().getSimpleName() + " save: guardar profesor con datos: {}", profesorDTO);

        this.profesorService.save(profesorDTO);
    }

    /**
     * Actualiza el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a actualizar
     */
    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody ProfesorDTO profesorDTO) {
        log.info(this.getClass().getSimpleName() + " update: actualizar profesor con id: {}", id);

        ProfesorDTO existeProfesor = this.profesorService.findById(id);
        if (existeProfesor == null) {
            log.error("El profesor con id {} no existe.", id);
        }
        existeProfesor.setNombre(profesorDTO.getNombre());
        existeProfesor.setApellidos(profesorDTO.getApellidos());
        existeProfesor.setContrasenya(profesorDTO.getContrasenya());
        existeProfesor.setNif(profesorDTO.getNif());
        existeProfesor.setDireccion(profesorDTO.getDireccion());
        existeProfesor.setEmail(profesorDTO.getEmail());
        existeProfesor.setTelefono(profesorDTO.getTelefono());

        this.profesorService.update(existeProfesor);
    }
}
