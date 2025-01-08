package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.CargoDTO;
import ies.camp.guardias.repository.dao.CargoRepository;

@Service
public class CargoServiceImpl implements CargoService {

    private static final Logger log = LoggerFactory.getLogger(CargoServiceImpl.class);

    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public List<CargoDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cargos");

        return this.cargoRepository.findAll().stream().map(c -> CargoDTO.convertToDTO(c)).collect(Collectors.toList());
    }

    @Override
    public CargoDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver cargo con id: {}", id);

        return this.cargoRepository.findById(id).map(CargoDTO::convertToDTO).orElse(null);
    }
}
