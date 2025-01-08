package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.SesionDTO;
import ies.camp.guardias.repository.dao.SesionRepository;

@Service
public class SesionServiceImpl implements SesionService {

    private static final Logger log = LoggerFactory.getLogger(SesionServiceImpl.class);

    @Autowired
    private SesionRepository sesionRepository;

    @Override
    public List<SesionDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todas las sesiones");

        return this.sesionRepository.findAll().stream().map(s -> SesionDTO.convertToDTO(s))
                .collect(Collectors.toList());
    }

    @Override
    public SesionDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver sesion con id: {}", id);

        return this.sesionRepository.findById(id).map(SesionDTO::convertToDTO).orElse(null);
    }

}
