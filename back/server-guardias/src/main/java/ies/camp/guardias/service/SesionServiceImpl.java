package ies.camp.guardias.service;

import ies.camp.guardias.model.dto.ProfesorDTO;
import ies.camp.guardias.model.dto.SesionDTO;
import ies.camp.guardias.repository.dao.AulaRepository;
import ies.camp.guardias.repository.dao.CargoRepository;
import ies.camp.guardias.repository.dao.CuadranteRepository;
import ies.camp.guardias.repository.dao.CursoRepository;
import ies.camp.guardias.repository.dao.DiaRepository;
import ies.camp.guardias.repository.dao.GrupoRepository;
import ies.camp.guardias.repository.dao.IntervaloRepository;
import ies.camp.guardias.repository.dao.MateriaRepository;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.dao.SesionRepository;
import ies.camp.guardias.repository.entity.Aula;
import ies.camp.guardias.repository.entity.Cargo;
import ies.camp.guardias.repository.entity.Cuadrante;
import ies.camp.guardias.repository.entity.Curso;
import ies.camp.guardias.repository.entity.Dia;
import ies.camp.guardias.repository.entity.Grupo;
import ies.camp.guardias.repository.entity.Intervalo;
import ies.camp.guardias.repository.entity.Materia;
import ies.camp.guardias.repository.entity.Profesor;
import ies.camp.guardias.repository.entity.Sesion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SesionServiceImpl implements SesionService {

        private static final Logger log = LoggerFactory.getLogger(
                        SesionServiceImpl.class);

        @Autowired
        private MateriaRepository materiaRepository;

        @Autowired
        private GrupoRepository grupoRepository;

        @Autowired
        private AulaRepository aulaRepository;

        @Autowired
        private ProfesorRepository profesorRepository;

        @Autowired
        private DiaRepository diaRepository;

        @Autowired
        private CursoRepository cursoRepository;

        @Autowired
        private SesionRepository sesionRepository;

        @Autowired
        private IntervaloRepository intervaloRepository;

        @Autowired
        private CuadranteRepository cuadranteRepository;

        @Autowired
        private CargoRepository cargoRepository;

        @Override
        public boolean loadFromCSV(MultipartFile csv, int year) {
                log.info(
                                this.getClass().getSimpleName() +
                                                " loadFromCSV: empezar a cargar la base de datos desde un CSV");

                // Carga archivo a un ArrayList
                ArrayList<String> lines = new ArrayList<>();
                try {
                        BufferedReader bufferedReader = new BufferedReader(
                                        new InputStreamReader(
                                                        csv.getInputStream(),
                                                        StandardCharsets.ISO_8859_1));
                        bufferedReader.lines().forEachOrdered(lines::add);
                        bufferedReader.close();
                } catch (IOException e) {
                        log.error(
                                        this.getClass().getSimpleName() +
                                                        " loadFromCSV: error leyendo el archivo: {}",
                                        e);
                        return false;
                }

                // HashSets para los datos existentes
                Set<Materia> materias = this.materiaRepository.findAll()
                                .stream()
                                .collect(Collectors.toSet());
                Set<Grupo> grupos = this.grupoRepository.findAll().stream().collect(Collectors.toSet());
                Set<Aula> aulas = this.aulaRepository.findAll().stream().collect(Collectors.toSet());
                Set<Profesor> profesores = this.profesorRepository.findAll()
                                .stream()
                                .collect(Collectors.toSet());

                // Creacion de objetos a guardar
                for (int i = 1; i < lines.size(); i++) {
                        List<String> fields = Arrays.asList(lines.get(i).split(";"));

                        if (fields.get(6).trim() != "") {
                                grupos.add(this.loadGrupo(fields.subList(6, 10)));
                        }
                        materias.add(this.loadMateria(fields.subList(0, 6)));
                        aulas.add(this.loadAula(fields.subList(10, 13)));
                        profesores.add(this.loadProfesor(fields.subList(13, 15)));
                }

                // Cargar las tablas
                try {
                        materias.forEach(this.materiaRepository::save);
                        grupos.forEach(this.grupoRepository::save);
                        aulas.forEach(this.aulaRepository::save);
                        profesores.forEach(this.profesorRepository::save);

                        this.cuadranteRepository.deleteAllInBatch();
                        this.sesionRepository.deleteAllInBatch();

                        this.loadSesiones(lines.subList(1, lines.size()));
                        this.loadCuadrantes(year);
                } catch (Exception e) {
                        log.error(
                                        this.getClass().getSimpleName() +
                                                        " loadFromCSV: error al guardar datos: {}",
                                        e);
                }
                return true;
        }

        private void loadCuadrantes(int year) {
                LocalDate start = LocalDate.of(year, 9, 9);
                LocalDate end = start.plusYears(1).withMonth(6).withDayOfMonth(18);

                List<Sesion> sesiones = this.sesionRepository.findSesionesGuardia();
                List<Intervalo> intervalos = this.intervaloRepository.findAll();
                List<Cargo> cargos = this.cargoRepository.findAll();

                List<Object> cuadrantes = new ArrayList<>();
                String[] dias = { "L", "M", "X", "J", "V" };

                while (!start.isAfter(end)) {
                        if (start.getDayOfWeek() == DayOfWeek.SATURDAY) {
                                start = start.plusDays(2);
                        } else {
                                final LocalDate startFinal = start;

                                List<Sesion> sesionesDia = sesiones
                                                .stream()
                                                .filter(ses -> ses
                                                                .getDia()
                                                                .getAbreviacion()
                                                                .equals(
                                                                                dias[startFinal.getDayOfWeek()
                                                                                                .getValue() - 1]))
                                                .collect(Collectors.toList());

                                for (Intervalo inter : intervalos) {
                                        List<Sesion> sesionesIntervalo = sesionesDia
                                                        .stream()
                                                        .filter(ses -> ses.getIntervalo().equals(inter))
                                                        .collect(Collectors.toList());

                                        Collections.shuffle(sesionesIntervalo);

                                        for (int i = 0; i < sesionesIntervalo.size(); i++) {
                                                cuadrantes.add(
                                                                Cuadrante.builder()
                                                                                .cargo(cargos.get(i))
                                                                                .guardia(sesionesIntervalo.get(i))
                                                                                .fecha(startFinal)
                                                                                .build());
                                        }
                                }
                                start = start.plusDays(1);
                        }
                }

                this.saveWithLimit(cuadrantes, 1000, this.cuadranteRepository);
        }

        private void loadSesiones(List<String> lines) {
                Curso curso = this.cursoRepository.getCurrent().orElse(null);

                // Cargar las tablas a HashTables para menor numero de peticiones a base de
                // datos
                Hashtable<Long, Profesor> profesores = new Hashtable<Long, Profesor>();
                this.profesorRepository.findAll()
                                .forEach(profesor -> profesores.put(profesor.getNumero(), profesor));
                Hashtable<Long, Materia> materias = new Hashtable<Long, Materia>();
                this.materiaRepository.findAll()
                                .forEach(materia -> materias.put(materia.getNumero(), materia));
                Hashtable<Long, Grupo> grupos = new Hashtable<Long, Grupo>();
                this.grupoRepository.findAll()
                                .forEach(grupo -> grupos.put(grupo.getNumero(), grupo));
                Hashtable<Long, Aula> aulas = new Hashtable<Long, Aula>();
                this.aulaRepository.findAll()
                                .forEach(aula -> aulas.put(aula.getNumero(), aula));
                Hashtable<String, Dia> dias = new Hashtable<String, Dia>();
                this.diaRepository.findAll()
                                .forEach(dia -> dias.put(dia.getAbreviacion(), dia));
                Hashtable<Long, Intervalo> intervalos = new Hashtable<Long, Intervalo>();
                this.intervaloRepository.findAll()
                                .forEach(intervalo -> intervalos.put(intervalo.getId(), intervalo));

                List<Object> sesiones = new ArrayList<>();

                for (String line : lines) {
                        String[] fields = line.split(";");
                        Long idIntervalo = Long.valueOf(fields[17]);
                        Long idGrupo = fields[6].trim() == ""
                                        ? null
                                        : Long.parseLong(fields[6].trim());

                        Profesor profesor = profesores.get(
                                        Long.parseLong(fields[13].trim()));
                        Materia materia = materias.get(Long.parseLong(fields[0].trim()));
                        Grupo grupo = idGrupo == null ? null : grupos.get(idGrupo);
                        Aula aula = aulas.get(Long.parseLong(fields[10].trim()));
                        Dia dia = dias.get(fields[16].trim());
                        Intervalo intervalo = intervalos.get(idIntervalo);

                        sesiones.add(
                                        Sesion.builder()
                                                        .profesor(profesor)
                                                        .materia(materia)
                                                        .grupo(grupo)
                                                        .aula(aula)
                                                        .intervalo(intervalo)
                                                        .curso(curso)
                                                        .dia(dia)
                                                        .build());
                }

                this.saveWithLimit(sesiones, 1000, this.sesionRepository);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        private void saveWithLimit(List<Object> list, int limit, Object repo) {
                for (int i = 0; i < list.size(); i += limit) {
                        ((JpaRepository) repo).saveAll(
                                        list.subList(i, Math.min(i + limit, list.size())));
                }
        }

        private Materia loadMateria(List<String> datos) {
                // Conversiones y seleccion atributos
                Long numero = Long.parseLong(datos.get(0).trim());
                String abrev = datos.get(1).trim();
                String nombre = datos.get(2).trim();
                String codigo = datos.get(3).trim();
                Integer horas = Integer.parseInt(datos.get(5).trim());

                return Materia.builder()
                                .numero(numero)
                                .abreviacion(abrev)
                                .nombre(nombre)
                                .codigo(codigo)
                                .horas(horas)
                                .build();
        }

        private Grupo loadGrupo(List<String> datos) {
                // Conversiones y seleccion atributos
                Long numero = Long.parseLong(datos.get(0).trim());
                String abrev = datos.get(1).trim();
                String nombre = datos.get(2).trim();
                String curso = datos.get(3).trim();

                return Grupo.builder()
                                .numero(numero)
                                .abreviacion(abrev)
                                .nombre(nombre)
                                .curso(curso)
                                .build();
        }

        private Aula loadAula(List<String> datos) {
                // Conversiones y seleccion atributos
                Long numero = Long.parseLong(datos.get(0).trim());
                String abrev = datos.get(1).trim();
                String nombre = datos.get(2).trim();

                return Aula.builder()
                                .numero(numero)
                                .abreviacion(abrev)
                                .nombre(nombre)
                                .build();
        }

        private Profesor loadProfesor(List<String> datos) {
                // Conversiones y seleccion atributos
                Long numero = Long.parseLong(datos.get(0).trim());
                String abrev = datos.get(1).trim();

                return Profesor.builder().numero(numero).abreviacion(abrev).build();
        }

        @Override
        public List<SesionDTO> findAllBySesiones(ProfesorDTO profesorDTO) {
                log.info(this.getClass().getSimpleName()
                                + " findAllBySesiones: Lista de todas las cuentas del cliente: {}",
                                profesorDTO.getId());

                List<Sesion> lista = (List<Sesion>) sesionRepository.findAllBySesiones(profesorDTO.getId());

                List<SesionDTO> listaResultadoDTO = new ArrayList<>();

                for (int i = 0; i < lista.size(); i++) {
                        Profesor profesorSesion = lista.get(i).getProfesor();
                        if (profesorSesion != null && profesorSesion.getActivo()) {
                                profesorSesion.setActivo(false);
                                profesorRepository.save(profesorSesion);

                                Profesor nuevoProfesor = new Profesor();
                                nuevoProfesor.setActivo(true);
                                profesorRepository.save(nuevoProfesor);
                                lista.get(i).setProfesor(nuevoProfesor);
                        }
                        listaResultadoDTO.add(SesionDTO.convertToDTO(lista.get(i)));
                }

                return listaResultadoDTO;
        }
}