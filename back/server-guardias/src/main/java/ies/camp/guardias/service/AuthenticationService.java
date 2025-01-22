package ies.camp.guardias.service;

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

    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ProfesorDTO login(LoginUsuarioDTO input) {
        ProfesorDTO profesor = this.profesorRepository.findByEmail(input.getEmail())
                .map(ProfesorDTO::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Email no encontrado: " + input.getEmail()));

        if (!passwordEncoder.matches(input.getPassword(), profesor.getContrasenya())) {
            throw new IllegalArgumentException("Contraseña incorrecta para el email: " + input.getEmail());
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return profesor;
    }
}
