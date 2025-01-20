package ies.camp.guardias.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ies.camp.guardias.repository.dao.AulaRepository;
import ies.camp.guardias.repository.dao.CursoRepository;
import ies.camp.guardias.repository.dao.DiaRepository;
import ies.camp.guardias.repository.dao.GrupoRepository;
import ies.camp.guardias.repository.dao.IntervaloRepository;
import ies.camp.guardias.repository.dao.MateriaRepository;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.dao.SesionRepository;
import ies.camp.guardias.repository.entity.Aula;
import ies.camp.guardias.repository.entity.Curso;
import ies.camp.guardias.repository.entity.Dia;
import ies.camp.guardias.repository.entity.Grupo;
import ies.camp.guardias.repository.entity.Intervalo;
import ies.camp.guardias.repository.entity.Materia;
import ies.camp.guardias.repository.entity.Profesor;
import ies.camp.guardias.repository.entity.Sesion;

@Service
public class SesionServiceImpl implements SesionService {

    private static final Logger log = LoggerFactory.getLogger(SesionServiceImpl.class);

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

    @Override
    public boolean loadFromCSV(MultipartFile csv) {
        log.info(this.getClass().getSimpleName() + " loadFromCSV: empezar a cargar la base de datos desde un CSV");

        // Carga archivo a un ArrayList
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(csv.getInputStream(), StandardCharsets.ISO_8859_1));
            bufferedReader.lines().forEachOrdered(lines::add);
            bufferedReader.close();
        } catch (IOException e) {
            log.error(this.getClass().getSimpleName() + " loadFromCSV: error leyendo el archivo: {}", e);
            return false;
        }

        // HashSets para los datos existentes
        Set<Materia> materias = this.materiaRepository.findAll().stream().collect(Collectors.toSet());
        Set<Grupo> grupos = this.grupoRepository.findAll().stream().collect(Collectors.toSet());
        Set<Aula> aulas = this.aulaRepository.findAll().stream().collect(Collectors.toSet());
        Set<Profesor> profesores = this.profesorRepository.findAll().stream().collect(Collectors.toSet());
        Set<Dia> dias = this.diaRepository.findAll().stream().collect(Collectors.toSet());

        // Creacion de objetos a guardar
        for (int i = 1; i < lines.size(); i++) {
            List<String> fields = Arrays.asList(lines.get(i).split(";"));

            if (fields.get(6).trim() != "") {
                grupos.add(this.loadGrupo(fields.subList(6, 10)));
            }
            materias.add(this.loadMateria(fields.subList(0, 6)));
            aulas.add(this.loadAula(fields.subList(10, 13)));
            profesores.add(this.loadProfesor(fields.subList(13, 15)));
            dias.add(this.loadDia(fields.get(16).trim()));
        }

        // Cargar las tablas
        materias.forEach(this.materiaRepository::save);
        grupos.forEach(this.grupoRepository::save);
        aulas.forEach(this.aulaRepository::save);
        profesores.forEach(this.profesorRepository::save);
        dias.forEach(this.diaRepository::save);

        this.loadSesiones(lines.subList(1, lines.size()));

        return true;
    }

    private void loadSesiones(List<String> lines) {
        Curso curso = this.cursoRepository.getCurrent().orElse(null);

        // Cargar las tablas a HashTables para menor numero de peticiones a base de
        // datos
        Hashtable<Long, Profesor> profesores = new Hashtable<Long, Profesor>();
        this.profesorRepository.findAll().forEach(profesor -> profesores.put(profesor.getNumero(), profesor));
        Hashtable<Long, Materia> materias = new Hashtable<Long, Materia>();
        this.materiaRepository.findAll().forEach(materia -> materias.put(materia.getNumero(), materia));
        Hashtable<Long, Grupo> grupos = new Hashtable<Long, Grupo>();
        this.grupoRepository.findAll().forEach(grupo -> grupos.put(grupo.getNumero(),
                grupo));
        Hashtable<Long, Aula> aulas = new Hashtable<Long, Aula>();
        this.aulaRepository.findAll().forEach(aula -> aulas.put(aula.getNumero(),
                aula));
        Hashtable<String, Dia> dias = new Hashtable<String, Dia>();
        this.diaRepository.findAll().forEach(dia -> dias.put(dia.getAbreviacion(),
                dia));
        Hashtable<Long, Intervalo> intervalos = new Hashtable<Long, Intervalo>();
        this.intervaloRepository.findAll().forEach(intervalo -> intervalos.put(intervalo.getId(), intervalo));

        for (String line : lines) {
            String[] fields = line.split(";");
            Long idIntervalo = Long.valueOf(fields[17]);
            Long idGrupo = fields[6].trim() == "" ? null : Long.parseLong(fields[6].trim());

            Profesor profesor = profesores.get(Long.parseLong(fields[13].trim()));
            Materia materia = materias.get(Long.parseLong(fields[0].trim()));
            Grupo grupo = idGrupo == null ? null : grupos.get(idGrupo);
            Aula aula = aulas.get(Long.parseLong(fields[10].trim()));
            Dia dia = dias.get(fields[16].trim());
            Intervalo intervalo = intervalos.get(idIntervalo);

            this.sesionRepository.save(Sesion.builder()
                    .profesor(profesor)
                    .materia(materia)
                    .grupo(grupo)
                    .aula(aula)
                    .intervalo(intervalo)
                    .curso(curso)
                    .dia(dia)
                    .build());
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

        return Profesor.builder()
                .numero(numero)
                .abreviacion(abrev)
                .build();
    }

    private Dia loadDia(String abrev) {
        // Conversiones y seleccion atributos
        Map<String, String> nombreDias = Map.of(
                "L", "Lunes",
                "M", "Martes",
                "X", "Miercoles",
                "J", "Jueves",
                "V", "Viernes");

        return Dia.builder()
                .abreviacion(abrev)
                .nombre(nombreDias.get(abrev))
                .build();
    }
}
