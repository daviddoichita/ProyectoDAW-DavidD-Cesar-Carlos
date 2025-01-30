package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.IntervaloDTO;
import ies.camp.guardias.repository.dao.IntervaloRepository;

@Service
public class IntervaloServiceImpl implements IntervaloService {

    private static final Logger log = LoggerFactory.getLogger(IntervaloServiceImpl.class);

    @Autowired
    private IntervaloRepository intervaloRepository;

    @Override
    public List<IntervaloDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los intervalos");

        return this.intervaloRepository.findAll().stream().map(IntervaloDTO::convertToDTO).collect(Collectors.toList());
    }

}
