package ies.camp.guardias.web.restController;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.camp.guardias.model.dto.LoginResponseDTO;
import ies.camp.guardias.model.dto.LoginUsuarioDTO;
import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.service.AuthenticationService;
import ies.camp.guardias.service.JwtService;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationRestController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationRestController.class);

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {
        try {
            ProfesorDTO profesor = authenticationService.login(loginUsuarioDTO);

            String jwtToken = jwtService.generateToken(profesor);
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponseDTO);
        } catch (IllegalArgumentException e) {
            log.warn(this.getClass().getSimpleName() + " login: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getAllErrors()
                .forEach(err -> {
                    String name = err.getObjectName();
                    String msg = err.getDefaultMessage();
                    errors.put(name, msg);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
