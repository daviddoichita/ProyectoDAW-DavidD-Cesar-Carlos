package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.CursoDTO;
import ies.camp.guardias.repository.dao.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {

    private static final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<CursoDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todos los cursos");

        return this.cursoRepository.findAll().stream().map(c -> CursoDTO.convertToDTO(c)).collect(Collectors.toList());
    }

    @Override
    public CursoDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver curso con id: {}", id);

        return this.cursoRepository.findById(id).map(CursoDTO::convertToDTO).orElse(null);
    }
}
