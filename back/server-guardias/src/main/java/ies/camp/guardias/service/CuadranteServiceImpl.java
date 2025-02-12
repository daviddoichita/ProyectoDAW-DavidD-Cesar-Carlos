package ies.camp.guardias.service;

import ies.camp.guardias.model.dto.CuadranteDTO;
import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.repository.dao.CuadranteRepository;
import ies.camp.guardias.repository.dao.FaltaRepository;
import ies.camp.guardias.repository.entity.Cuadrante;
import ies.camp.guardias.repository.entity.Falta;
import ies.camp.guardias.repository.entity.Sesion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CuadranteServiceImpl implements CuadranteService {

        private static final Logger log = LoggerFactory.getLogger(
                        CuadranteServiceImpl.class);

        @Autowired
        private CuadranteRepository cuadranteRepository;

        @Autowired
        private FaltaRepository faltaRepository;

        @Override
        public List<CuadranteDTO> findAll() {
                log.info(
                                this.getClass().getSimpleName() +
                                                " findAll: devolver todos los cuadrantes");

                return this.cuadranteRepository.findAll()
                                .stream()
                                .map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public CuadranteDTO findById(Long id) {
                log.info(
                                this.getClass().getSimpleName() +
                                                " findById: devolver cuadrante con id: {}",
                                id);

                return this.cuadranteRepository.findById(id)
                                .map(CuadranteDTO::convertToDTO)
                                .orElse(null);
        }

        @Override
        public List<CuadranteDTO> findByRange(LocalDate start, LocalDate end) {
                log.info(
                                this.getClass().getSimpleName() +
                                                " findByRange: devolver los cuadrantes dentro de las fechas [ {} : {} ]",
                                start,
                                end);

                return this.cuadranteRepository.findByRange(start, end)
                                .stream()
                                .map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public List<CuadranteDTO> findCurrentWeek() {
                log.info(
                                this.getClass().getSimpleName() +
                                                " findCurrentWeek: devolver la semana actual");

                LocalDate start = LocalDate.now();
                if (start.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                                start.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        start = start.with(DayOfWeek.MONDAY);
                }
                LocalDate end = LocalDate.now().with(DayOfWeek.FRIDAY);

                return this.cuadranteRepository.findByRange(start, end)
                                .stream()
                                .map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public List<CuadranteDTO> findToday() {
                log.info(
                                this.getClass().getSimpleName() +
                                                " findToday: devolver cuadrantes del dia actual");

                LocalDate now = LocalDate.now();
                return this.cuadranteRepository.findByRange(now, now)
                                .stream()
                                .map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public int firmarCuadrante(UserDetails currentUser, Long id, Long idFalta, String firma) {
                log.info(this.getClass().getSimpleName() + " firmarCuadrante: firmar cuadrante con id: {}", id);

                Optional<Cuadrante> cuadranteOptional = this.cuadranteRepository.findById(id);
                int estado = 1;
                if (cuadranteOptional.isPresent()) {
                        Cuadrante cuadrante = cuadranteOptional.get();

                        ProfesorDTO profesorDTO = (ProfesorDTO) currentUser;
                        if (profesorDTO.getEmail().equals(cuadrante.getGuardia().getProfesor().getEmail())) {
                                Falta falta = cuadrante.getFaltas().stream().filter(f -> f.getId().equals(idFalta))
                                                .collect(Collectors.toList()).get(0);
                                falta.setFirma(firma);
                                Falta saved = this.faltaRepository.save(falta);
                                if (saved != null) {
                                        estado = 0;
                                }
                        } else {
                                estado = 2;
                        }

                }

                return estado;
        }

        @Override
        public List<CuadranteDTO> findCuadranteConFaltas() {
                log.info(this.getClass().getSimpleName()
                                + " findCuadranteConFalta: devolver los cuadrantes con faltas");

                return this.cuadranteRepository.findCuadrantesConFaltas().stream().map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public List<CuadranteDTO> findCuadranteSinFirmar() {
                log.info(this.getClass().getSimpleName()
                                + " findCuadranteSinFirmar: devolver los cuadrantes sin firmar");

                return this.cuadranteRepository.findCuadrantesSinFirmar().stream().map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public List<CuadranteDTO> findCuadranteConIncidencias() {
                log.info(this.getClass().getSimpleName()
                                + " findCuadranteConIncidencia: devolver los cuadrantes con incidencias");

                return this.cuadranteRepository.findCuadrantesConIncidencias().stream().map(CuadranteDTO::convertToDTO)
                                .collect(Collectors.toList());
        }

}
