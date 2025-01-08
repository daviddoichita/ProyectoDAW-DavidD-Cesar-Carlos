package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.GrupoDTO;
import ies.camp.guardias.repository.dao.GrupoRepository;

@Service
public class GrupoServiceImpl implements GrupoService {

    private static final Logger log = LoggerFactory.getLogger(GrupoServiceImpl.class);

    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public List<GrupoDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los grupos");

        return this.grupoRepository.findAll().stream().map(g -> GrupoDTO.convertToDTO(g)).collect(Collectors.toList());
    }

    @Override
    public GrupoDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver el grupo con id: {}", id);

        return this.grupoRepository.findById(id).map(GrupoDTO::convertToDTO).orElse(null);
    }
}
