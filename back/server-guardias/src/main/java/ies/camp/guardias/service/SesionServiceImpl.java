package ies.camp.guardias.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ies.camp.guardias.repository.dao.GrupoRepository;
import ies.camp.guardias.repository.dao.MateriaRepository;
import ies.camp.guardias.repository.entity.Grupo;
import ies.camp.guardias.repository.entity.Materia;
import jakarta.transaction.Transactional;

@Service
public class SesionServiceImpl implements SesionService {

    private static final Logger log = LoggerFactory.getLogger(SesionServiceImpl.class);

    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private GrupoRepository grupoRepository;

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

            // Creacion de objetos a guardar
            for (int i = 1; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(";");

                // Conversiones y seleccion atributos materia
                Long numero = Long.parseLong(fields[0].trim());
                String abrev = fields[1].trim();
                String nombre = fields[2].trim();
                String codigo = fields[3].trim().replace(" ", "");
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
            }

            // Cargar las tablas
            materias.forEach(this.materiaRepository::save);
            grupos.forEach(this.grupoRepository::save);

        } catch (IOException e) {
            log.error(this.getClass().getSimpleName() + " loadFromCSV: error leyendo el archivo: {}", e);
            return false;
        }

        return true;
    }

}
