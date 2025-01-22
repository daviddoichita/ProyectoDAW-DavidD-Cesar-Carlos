package ies.camp.guardias.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.entity.Profesor;

@Service
public class ProfesorServiceImpl implements ProfesorService, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(ProfesorServiceImpl.class);

	@Autowired
	private ProfesorRepository profesorRepository;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

		if (usuario.contains("@")) { // Comprobar si es Email o no (si contiene @ o no)
			Profesor profesor = profesorRepository.findByEmail(usuario).orElseThrow(() -> {
				log.error("No se ha encontrado al profesor con email: {}", usuario);
				return new RuntimeException("No se ha encontrado al profesor");
			});

			if (profesor != null) {
				Collection<GrantedAuthority> rol = new ArrayList<>();
				rol.add(new SimpleGrantedAuthority(profesor.getRol()));
	
				return new User(profesor.getEmail(), profesor.getContrasenya(), rol);
	
			} else {
				throw new UsernameNotFoundException(usuario);
			}

		} else{
			Profesor profesor = profesorRepository.findByNif(usuario).orElseThrow(() -> {
				log.error("No se ha encontrado al profesor con nif: {}", usuario);
				return new RuntimeException("No se ha encontrado al profesor");
			});

			if (profesor != null) {
			
				Collection<GrantedAuthority> rol = new ArrayList<>();
				rol.add(new SimpleGrantedAuthority(profesor.getRol()));
	
				return new User(profesor.getNif(), profesor.getContrasenya(), rol);
	
			} else {
				throw new UsernameNotFoundException(usuario);
			}
		}
	}

	@Override
	public ProfesorDTO findByEmail(String email) {
		Profesor p = this.profesorRepository.findByEmail(email).orElse(null);
		p.setContrasenya(null);
		return ProfesorDTO.convertToDTO(p);
	}

	@Override
    public ProfesorDTO findByNif(String nif) {
        Profesor p = this.profesorRepository.findByNif(nif).orElse(null);
        p.setContrasenya(null);
        return ProfesorDTO.convertToDTO(p);
    }

	@Override
	public void login(String usuario, String contrasenya) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuario, contrasenya);
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(token);
	}

}