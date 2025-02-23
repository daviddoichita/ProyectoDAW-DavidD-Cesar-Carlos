package ies.camp.guardias.service;

import ies.camp.guardias.model.dto.FaltaDTO;
import ies.camp.guardias.repository.dao.FaltaRepository;
import ies.camp.guardias.repository.entity.Falta;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaltaServiceImpl implements FaltaService {

    private final FaltaRepository faltaRepository;

    @Override
    public List<FaltaDTO> obtenerTodasLasFaltas() {
        return faltaRepository.findAll()
                .stream()
                .map(FaltaDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FaltaDTO obtenerFaltaPorId(Long id) {
        return faltaRepository.findById(id)
                .map(FaltaDTO::convertToDTO)
                .orElse(null);
    }

    @Override
    public FaltaDTO registrarFalta(FaltaDTO faltaDTO) {
        Falta falta = FaltaDTO.convertToEntity(faltaDTO);
        Falta faltaGuardada = faltaRepository.save(falta);
        return FaltaDTO.convertToDTO(faltaGuardada);
    }

    @Override
    public void eliminarFalta(Long id) {
        faltaRepository.deleteById(id);
    }
}