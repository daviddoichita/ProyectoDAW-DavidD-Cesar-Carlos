package ies.camp.guardias.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.LoginUsuarioDTO;
import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.ProfesorRepository;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ProfesorDTO login(LoginUsuarioDTO input) {
        ProfesorDTO profesor = null;
        log.info(this.getClass().getSimpleName() + " login: validar entrada: {}", input);
        if (input.getEmail() != null) {
            log.info(this.getClass().getSimpleName() + " login: autenticando por email");
            profesor = this.profesorRepository.findByEmail(input.getEmail())
                    .map(ProfesorDTO::convertToDTO)
                    .orElseThrow(() -> new IllegalArgumentException("Email no encontrado: " + input.getEmail()));
        } else if (input.getNif() != null) {
            log.info(this.getClass().getSimpleName() + " login: autenticando por nif");
            profesor = this.profesorRepository.findByNif(input.getNif()).map(ProfesorDTO::convertToDTO)
                    .orElseThrow(() -> new IllegalArgumentException("NIF no encontrado: " + input.getNif()));
        }

        if (!passwordEncoder.matches(input.getPassword(), profesor.getContrasenya())) {
            throw new IllegalArgumentException("Contraseña incorrecta para el email: " + input.getEmail());
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        input.getEmail() != null ? input.getEmail() : input.getNif(), input.getPassword()));

        return profesor;
    }
}
