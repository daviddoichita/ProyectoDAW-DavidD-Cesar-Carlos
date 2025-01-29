package ies.camp.guardias.web.restController;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.service.JwtService;
import ies.camp.guardias.service.ProfesorService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfesorRestController {

    private static final Logger log = LoggerFactory.getLogger(
        ProfesorRestController.class
    );

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private JwtService jwtService;

    @GetMapping(path = "/api/me")
    public ResponseEntity<Map<String, Object>> me() {
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

        String token = this.jwtService.generateToken(currentUser);

        Map<String, Object> response = Map.of(
            "user",
            currentUser,
            "token",
            token
        );

        log.info(
            this.getClass().getSimpleName() + " me: devolver usuario logeado"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/api/authLevel")
    public ResponseEntity<Map<String, Object>> authLevel() {
        log.info(
            this.getClass().getSimpleName() +
            " authLevel: devolver nivel de autoridad profesor"
        );

        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

        Map<String, Object> response = Map.of(
            "authLevel",
            currentUser.getAuthorities().toArray()[0].toString(),
            "status",
            "success"
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Devuelve todos los ProfesorDTO en formato JSON
     *
     * @return lista de ProfesorDTO
     */
    @GetMapping(path = "/api/profesores")
    @PreAuthorize("hasRole('DIRECCION')")
    public List<ProfesorDTO> findAll() {
        log.info(
            this.getClass().getSimpleName() +
            " findAll: devolver todos los profesores"
        );

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
    @PreAuthorize("hasRole('DIRECCION')")
    public ProfesorDTO findById(@PathVariable Long id) {
        log.info(
            this.getClass().getSimpleName() +
            " findById: devolver profesor con id: {}",
            id
        );

        return this.profesorService.findById(id);
    }

    /**
     * Borra el Profesor con id introducido
     *
     * @param id ID del Profesor a borrar
     */
    @GetMapping(path = "/api/profesores/{id}/delete")
    @PreAuthorize("hasRole('DIRECCION')")
    public void delete(@PathVariable Long id) {
        log.info(
            this.getClass().getSimpleName() +
            " deleteById: borrar profesor con id: {}",
            id
        );

        this.profesorService.delete(id);
    }

    /**
     * Guarda el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a guardar
     */
    @PostMapping(path = "/api/profesores/save")
    @PreAuthorize("hasRole('DIRECCION')")
    public void save(@RequestBody ProfesorDTO profesorDTO) {
        log.info(
            this.getClass().getSimpleName() +
            " save: guardar profesor con id: {}",
            profesorDTO.getId()
        );
        this.profesorService.save(profesorDTO);
    }
}
