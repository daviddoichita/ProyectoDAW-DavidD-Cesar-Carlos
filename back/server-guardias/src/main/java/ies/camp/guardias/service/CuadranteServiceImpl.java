package ies.camp.guardias.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.camp.guardias.model.dto.CuadranteDTO;
import ies.camp.guardias.repository.dao.CuadranteRepository;
import ies.camp.guardias.repository.dao.CursoRepository;
import ies.camp.guardias.repository.dao.SesionRepository;
import ies.camp.guardias.repository.entity.Cuadrante;
import ies.camp.guardias.repository.entity.Sesion;

@Service
public class CuadranteServiceImpl implements CuadranteService {

    private static final Logger log = LoggerFactory.getLogger(CuadranteServiceImpl.class);

    @Autowired
    private CuadranteRepository cuadranteRepository;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private CursoRepository cursoRepository;

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
        log.info(this.getClass().getSimpleName() + " findByRange: devolver cuadrantes en rango: [{}, {}]", start, end);

        return this.cuadranteRepository.findAll().stream()
                .filter(c -> !c.getFecha().isBefore(start) && !c.getFecha().isAfter(end))
                .map(CuadranteDTO::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean generate(boolean asignarRoles) {
        log.info(this.getClass().getSimpleName() + " generate: generar cuadrantes a partir de las sesiones");

        this.cuadranteRepository.deleteAll();
        log.info(this.getClass().getSimpleName() + " generate: cuadrantes borrados");

        List<Sesion> sesiones = this.sesionRepository.findAll();
        List<Cuadrante> semanaBase = new ArrayList<>();

        for (Sesion sesion : sesiones) {
            if (sesion.getGrupo() == null && sesion.getMateria() == null && sesion.getAula() == null) {
                Cuadrante cuadrante = Cuadrante.builder()
                        .intervalo(sesion.getIntervalo())
                        .profesor(sesion.getProfesor())
                        .curso(this.cursoRepository.getLast())
                        .incidencias("")
                        .firma("")
                        .deberes(false)
                        .build();

                log.info(this.getClass().getSimpleName() + " generate: generado entrada cuadrante: {}", cuadrante);

                semanaBase.add(cuadrante);
            }
        }

        List<Cuadrante> anyo = new ArrayList<>();
        for (LocalDate curr : this.getFechasCuso()) {
            for (Cuadrante cuadrante : semanaBase) {
                try {
                    Cuadrante completo = cuadrante.clone();
                    completo.setFecha(curr);
                    anyo.add(completo);

                } catch (CloneNotSupportedException e) {
                    log.error(this.getClass().getSimpleName() + " generate: error clonando cuadrante: {}", e);
                    return false;
                }
            }
        }

        anyo.forEach(this.cuadranteRepository::save);
        log.info(this.getClass().getSimpleName() + " generate: cuadrantes guardados");

        return true;
    }

    @Override
    public List<LocalDate> getFechasCuso() {
        log.info(this.getClass().getSimpleName() + " getFechasCurso: generar LocalDateTime de las fechas del curso");

        List<LocalDate> fechas = new ArrayList<>();
        LocalDate primerDia = LocalDate.of(LocalDate.now().getYear(), Month.SEPTEMBER, 11);
        LocalDate ultimoDia = LocalDate.of(LocalDate.now().plusYears(1).getYear(), Month.JUNE, 22);

        while (!primerDia.isAfter(ultimoDia)) {
            if (primerDia.getDayOfWeek() != DayOfWeek.SATURDAY) {
                fechas.add(primerDia);
                primerDia = primerDia.plusDays(1);
            } else {
                primerDia = primerDia.plusDays(2);
            }
        }

        return fechas;
    }

}
