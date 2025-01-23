package ies.camp.guardias.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ies.camp.guardias.model.dto.CuadranteDTO;
import ies.camp.guardias.repository.dao.CuadranteRepository;

public class CuadranteServiceImpl implements CuadranteService {

    private static final Logger log = LoggerFactory.getLogger(CuadranteServiceImpl.class);

    @Autowired
    private CuadranteRepository cuadranteRepository;

    @Override
    public List<CuadranteDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cuadrantes");

        return this.cuadranteRepository.findAll().stream().map(CuadranteDTO::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CuadranteDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver cuadrante con id: {}", id);

        return this.cuadranteRepository.findById(id).map(CuadranteDTO::convertToDTO).orElse(null);
    }

    @Override
    public List<CuadranteDTO> findByRange(LocalDate start, LocalDate end) {
        log.info(this.getClass().getSimpleName()
                + " findByRange: devolver los cuadrantes dentro de las fechas [ {} : {} ]", start, end);

        return this.cuadranteRepository.findAll().stream()
                .filter(c -> !c.getFecha().isBefore(start) && !c.getFecha().isAfter(end))
                .map(CuadranteDTO::convertToDTO).collect(Collectors.toList());
    }

}
