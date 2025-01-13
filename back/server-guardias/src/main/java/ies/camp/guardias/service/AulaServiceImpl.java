package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.AulaDTO;
import ies.camp.guardias.repository.dao.AulaRepository;

@Service
public class AulaServiceImpl implements AulaService {

    private static final Logger log = LoggerFactory.getLogger(AulaServiceImpl.class);

    @Autowired
    private AulaRepository aulaRepository;

    @Override
    public List<AulaDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todas las aulas");

        return this.aulaRepository.findAll().stream().map(AulaDTO::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public AulaDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver aula con id: {}", id);

        return this.aulaRepository.findById(id).map(AulaDTO::convertToDTO).orElse(null);
    }

}
