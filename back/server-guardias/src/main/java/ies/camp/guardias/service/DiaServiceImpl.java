package ies.camp.guardias.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.DiaDTO;
import ies.camp.guardias.repository.dao.DiaRepository;

@Service
public class DiaServiceImpl implements DiaService {

    private static final Logger log = LoggerFactory.getLogger(DiaServiceImpl.class);

    @Autowired
    private DiaRepository diaRepository;

    @Override
    public List<DiaDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los dias");

        return this.diaRepository.findAll().stream().map(DiaDTO::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public DiaDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver el dia con id: {}", id);

        return this.diaRepository.findById(id).map(DiaDTO::convertToDTO).orElse(null);
    }

    @Override
    public DiaDTO findByAbreviacion(String abreviacion) {
        log.info(this.getClass().getSimpleName() + " findByAbreviacion: devolver dia con abreviacion: {}", abreviacion);

        return this.diaRepository.findByAbreviacion(abreviacion).map(DiaDTO::convertToDTO).orElse(null);
    }

    @Override
    public List<DiaDTO> findByAbreviaciones(List<String> abreviaciones) {
        log.info(this.getClass().getSimpleName() + " findByAbreviaciones: devolver dias con abreviaciones: {}",
                Arrays.toString(abreviaciones.toArray()));

        return this.diaRepository.findByAbreviaciones(abreviaciones).stream().map(DiaDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiaDTO> findCurrentWeek() {
        log.info(this.getClass().getSimpleName() + " findCurrentWeek: devolver los dias de hoy al viernes");

        List<String> abreviaciones = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate end = today.with(DayOfWeek.FRIDAY).plusDays(1);

        while (today.isBefore(end)) {
            abreviaciones.add(
                    switch (today.getDayOfWeek()) {
                        case MONDAY -> "L";
                        case TUESDAY -> "M";
                        case WEDNESDAY -> "X";
                        case THURSDAY -> "J";
                        case FRIDAY -> "V";
                        default -> "";
                    });
            today = today.plusDays(1);
        }

        return this.diaRepository.findByAbreviaciones(abreviaciones).stream().map(DiaDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
