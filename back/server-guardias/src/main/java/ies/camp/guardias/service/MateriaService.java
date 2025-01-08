package ies.camp.guardias.service;

import java.util.List;

import ies.camp.guardias.model.dto.MateriaDTO;

public interface MateriaService {

    public List<MateriaDTO> findAll();

    public MateriaDTO findById(Long id);
}
