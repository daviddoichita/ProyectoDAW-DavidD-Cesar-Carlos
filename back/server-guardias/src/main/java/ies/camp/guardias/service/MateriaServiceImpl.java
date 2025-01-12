package ies.camp.guardias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.MateriaDTO;
import ies.camp.guardias.repository.dao.MateriaRepository;

@Service
public class MateriaServiceImpl implements MateriaService {

    private static final Logger log = LoggerFactory.getLogger(MateriaServiceImpl.class);

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Devuelve todas las Materias convertidas a MateriaDTO
     *
     * @return lista de MateriaDTO
     */
    @Override
    public List<MateriaDTO> findAll() {
        log.info(this.getClass().getSimpleName() + " findAll: devolver todas las materias");

        return this.materiaRepository.findAll().stream().map(m -> MateriaDTO.convertToDTO(m))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve la MateriaDTO asociada al id introducido o null si no existe
     *
     * @param id ID de la Materia a buscar
     * @return Materia si encuentra el id introducido o null en caso contrario
     */
    @Override
    public MateriaDTO findById(Long id) {
        log.info(this.getClass().getSimpleName() + " findById: devolver materia con id: {}", id);

        return this.materiaRepository.findById(id).map(MateriaDTO::convertToDTO).orElse(null);
    }

}
