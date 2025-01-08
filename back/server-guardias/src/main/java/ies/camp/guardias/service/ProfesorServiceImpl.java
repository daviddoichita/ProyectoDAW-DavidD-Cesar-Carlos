package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.ProfesorRepository;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private static final Logger log = LoggerFactory.getLogger(ProfesorServiceImpl.class);

    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public List<ProfesorDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los profesores");

        return this.profesorRepository.findAll().stream().map(p -> ProfesorDTO.convertToDTO(p))
                .collect(Collectors.toList());
    }

    @Override
    public ProfesorDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver profesor con id: {}", id);

        return this.profesorRepository.findById(id).map(ProfesorDTO::convertToDTO).orElse(null);
    }

}
