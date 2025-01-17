package ies.camp.guardias.service;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.plaf.nimbus.NimbusStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ies.camp.guardias.repository.dao.AulaRepository;
import ies.camp.guardias.repository.dao.DiaRepository;
import ies.camp.guardias.repository.dao.GrupoRepository;
import ies.camp.guardias.repository.dao.MateriaRepository;
import ies.camp.guardias.repository.dao.ProfesorRepository;
import ies.camp.guardias.repository.entity.Aula;
import ies.camp.guardias.repository.entity.Dia;
import ies.camp.guardias.repository.entity.Grupo;
import ies.camp.guardias.repository.entity.Materia;
import ies.camp.guardias.repository.entity.Profesor;

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

    @Override
    public boolean loadFromCSV(MultipartFile csv) {
        log.info(this.getClass().getSimpleName() + " loadFromCSV: empezar a cargar la base de datos desde un CSV");

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(csv.getInputStream(), StandardCharsets.ISO_8859_1));
            ArrayList<String> lines = new ArrayList<>();

            bufferedReader.lines().forEachOrdered(lines::add);
            // log.warn(this.getClass().getSimpleName() + " loadFromCSV: archivo csv: {}",
            // stringBuilder.toString());

            // HashSet para filtrar las materias
            Set<Materia> materias = this.materiaRepository.findAll().stream().collect(Collectors.toSet());
            Set<Grupo> grupos = this.grupoRepository.findAll().stream().collect(Collectors.toSet());
            Set<Aula> aulas = this.aulaRepository.findAll().stream().collect(Collectors.toSet());
            Set<Profesor> profesores = this.profesorRepository.findAll().stream().collect(Collectors.toSet());
            Set<Dia> dias = this.diaRepository.findAll().stream().collect(Collectors.toSet());

            // Creacion de objetos a guardar
            for (int i = 1; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(";");

                // Conversiones y seleccion atributos materia
                Long numero = Long.parseLong(fields[0].trim());
                String abrev = fields[1].trim();
                String nombre = fields[2].trim();
                String codigo = fields[3].trim();
                Integer horas = Integer.parseInt(fields[5].trim());
                // Filtrar las materias que ya existen
                materias.add(Materia.builder()
                        .numero(numero)
                        .abreviacion(abrev)
                        .nombre(nombre)
                        .codigo(codigo)
                        .horas(horas)
                        .build());

                if (fields[6].trim() != "") {
                    // Conversiones y seleccion atributos grupo
                    numero = Long.parseLong(fields[6].trim());
                    abrev = fields[7].trim();
                    nombre = fields[8].trim();
                    String curso = fields[9].trim();
                    // Filtrar los grupos que ya existen
                    grupos.add(Grupo.builder()
                            .numero(numero)
                            .abreviacion(abrev)
                            .nombre(nombre)
                            .curso(curso)
                            .build());
                }

                // Conversiones y seleccion atributos aula
                numero = Long.parseLong(fields[10].trim());
                abrev = fields[11].trim();
                nombre = fields[12].trim();
                // Filtrar las aulas que ya existen
                aulas.add(Aula.builder()
                        .numero(numero)
                        .abreviacion(abrev)
                        .nombre(nombre)
                        .build());

                // Conversiones y seleccion atributos profesor
                numero = Long.parseLong(fields[13].trim());
                abrev = fields[14].trim();
                // Filtrar los profesores que ya existen
                profesores.add(Profesor.builder()
                        .numero(numero)
                        .abreviacion(abrev)
                        .build());

                // Conversiones y seleccion atributos horas
                Map<String, String> nombreDias = Map.of(
                    "L", "Lunes",
                    "M", "Martes",
                    "X", "Miercoles",
                    "J", "Jueves",
                    "V", "Viernes"
                );
                abrev = fields[16].trim();
                nombre = nombreDias.get(abrev);
                // Filtrar dias que ya existen
                dias.add(Dia.builder()
                        .abreviacion(abrev)
                        .nombre(nombre)
                        .build());
            }

            // Cargar las tablas
            materias.forEach(this.materiaRepository::save);
            grupos.forEach(this.grupoRepository::save);
            aulas.forEach(this.aulaRepository::save);
            profesores.forEach(this.profesorRepository::save);
            dias.forEach(this.diaRepository::save);

        } catch (IOException e) {
            log.error(this.getClass().getSimpleName() + " loadFromCSV: error leyendo el archivo: {}", e);
            return false;
        }

        return true;
    }

}
