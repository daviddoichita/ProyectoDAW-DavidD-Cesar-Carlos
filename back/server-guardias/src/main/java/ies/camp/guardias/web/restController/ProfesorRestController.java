package ies.camp.guardias.web.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.entity.Profesor;
import ies.camp.guardias.security.JwtTokenProvider;
import ies.camp.guardias.service.ProfesorService;

@RestController
public class ProfesorRestController {

    private static final Logger log = LoggerFactory.getLogger(ProfesorRestController.class);

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Devuelve todos los ProfesorDTO en formato JSON
     *
     * @return lista de ProfesorDTO
     */
    @GetMapping(path = "/api/profesores")
    public List<ProfesorDTO> findAll(@AuthenticationPrincipal User user) {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");
        log.info(this.getClass().getSimpleName() + "Usuario logeado:" + user);

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
    @GetMapping(path = "/api/profesores/{id}/delete")
    public void delete(@PathVariable Long id) {
        log.info(this.getClass().getSimpleName() + " deleteById: borrar profesor con id: {}", id);

        this.profesorService.delete(id);
    }

    /**
     * Guarda el ProfesorDTO introducido en la base de datos
     *
     * @param profesorDTO ProfesorDTO a guardar
     */
    @PostMapping(path = "/api/profesores/save")
    public void save(@RequestBody ProfesorDTO profesorDTO) {
        log.info(this.getClass().getSimpleName() + " save: guardar profesor con id: {}", profesorDTO.getId());
        this.profesorService.save(profesorDTO);
    }

    @GetMapping(path = "/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestParam String usuario, @RequestParam String contrasenya) {
        log.info("Intentando iniciar sesión para el email: {}", usuario);
        try {
            profesorService.login(usuario, contrasenya);
            String username =  SecurityContextHolder.getContext().getAuthentication().getName();
            String token;
            ProfesorDTO profesor;

            if (username.contains("@")) { // Comprobar si es Email o no (si contiene @ o no)
                 profesor = this.profesorService.findByEmail(username);
                 token = jwtTokenProvider.generateToken(profesor.getEmail());

            } else {
                profesor = this.profesorService.findByNif(username);
                token = jwtTokenProvider.generateToken(profesor.getNif());
            }

            return ResponseEntity.ok(new JwtAuthenticationResponse(token));

        } catch (Exception e) {
            log.error("Error en el inicio de sesión para el email: {} \n {}", usuario, e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}