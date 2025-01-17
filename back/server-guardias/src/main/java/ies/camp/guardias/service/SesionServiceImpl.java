package ies.camp.guardias.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SesionServiceImpl implements SesionService {

    private static final Logger log = LoggerFactory.getLogger(SesionServiceImpl.class);

    @Override
    public boolean loadFromCSV(MultipartFile csv) {
        log.info(this.getClass().getSimpleName() + " loadFromCSV: empezar a cargar la base de datos desde un CSV");

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(csv.getInputStream(), StandardCharsets.ISO_8859_1));
            StringBuilder stringBuilder = new StringBuilder();

            bufferedReader.lines().forEach(stringBuilder::append);

            log.warn(this.getClass().getSimpleName() + " loadFromCSV: archivo csv: {}", stringBuilder.toString());

        } catch (IOException e) {
            log.error(this.getClass().getSimpleName() + " loadFromCSV: error leyendo el archivo: {}", e);
            return false;
        }

        return false;
    }

}
