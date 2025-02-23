package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.FaltaDTO;

public interface FaltaService {

    List<FaltaDTO> obtenerTodasLasFaltas();

    FaltaDTO obtenerFaltaPorId(Long id);

    FaltaDTO registrarFalta(FaltaDTO faltaDTO);
    
    void eliminarFalta(Long id);
    
}