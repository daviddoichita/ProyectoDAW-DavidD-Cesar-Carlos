package ies.camp.guardias.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.entity.Profesor;

@Service
public class ProfesorServiceImpl implements ProfesorService {

	private static final Logger log = LoggerFactory.getLogger(ProfesorServiceImpl.class);

	@Autowired
	private ProfesorRepository profesorRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * @Override public List<ProfesorDTO> findAll() {
	 * log.info(this.getClass().getSimpleName() +
	 * " findAll: devolver todos los profesores");
	 * 
	 * return this.profesorRepository.findAll().stream().map(p ->
	 * ProfesorDTO.convertToDTO(p)) .collect(Collectors.toList()); }
	 */

	@Override
	public List<ProfesorDTO> findAll() {
		log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");

		List<ProfesorDTO> listaProfesorDTO = new ArrayList<ProfesorDTO>();
		List<Profesor> listaProfesor = profesorRepository.findAll();
		for (int i = 0; i < listaProfesor.size(); i++) {
			Profesor profesor = listaProfesor.get(i);
			ProfesorDTO profesorDTO = ProfesorDTO.convertToDTO(profesor);
			listaProfesorDTO.add(profesorDTO);
		}
		return listaProfesorDTO;
	}

	/*
	 * @Override public ProfesorDTO findById(Long id) {
	 * log.info(this.getClass().getSimpleName() +
	 * " findById: devolver profesor con id: {}", id);
	 * 
	 * return
	 * this.profesorRepository.findById(id).map(ProfesorDTO::convertToDTO).orElse(
	 * null); }
	 */

	@Override
	public ProfesorDTO findById(Long id) {
		log.info(this.getClass().getSimpleName() + " findById: devolver todos los profesores");

		Optional<Profesor> profesor = profesorRepository.findById(id);
		if (profesor.isPresent()) {
			ProfesorDTO profesorDTO = ProfesorDTO.convertToDTO(profesor.get());
			return profesorDTO;
		} else {
			return null;
		}
	}

	@Override
	public void delete(Long id) {
		log.info(this.getClass().getSimpleName() + " delete: borrar profesor con id: {}", id);

		profesorRepository.deleteById(id);
	}

	@Override
	public void save(ProfesorDTO profesorDTO) {
		log.info(this.getClass().getSimpleName() + " save: guardar profesor con id: {}", profesorDTO.getId());
		// Cifrar la contraseña antes de guardar
		profesorDTO.setContrasenya(passwordEncoder.encode(profesorDTO.getContrasenya()));
		Profesor profesor = ProfesorDTO.convertToEntity(profesorDTO);
		profesorRepository.save(profesor);
	}

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profesor profesor = profesorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profesor con email: " + email + " no encontrado"));

        return new User(profesor.getEmail(), profesor.getContrasenya(), List.of());
    }

    @Override
    public String login(String email, String contrasenya) {
		
        Profesor profesor = profesorRepository.findByEmail(email).orElseThrow(() -> {
            log.error("No se ha encontrado al profesor con email: {}", email);
            return new RuntimeException("No se ha encontrado al profesor");
        });

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, contrasenya));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return String.valueOf(profesor.getId());

        } catch (BadCredentialsException e) {
            log.error("Credenciales incorrectas para el email: {}", email);
            throw new RuntimeException("Email o contraseña incorrectos");
        }
    }

	public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "cesar1";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Contraseña cifrada: " + encodedPassword);
    }

	
}