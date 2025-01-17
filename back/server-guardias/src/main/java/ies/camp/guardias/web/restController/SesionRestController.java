package ies.camp.guardias.web.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ies.camp.guardias.service.SesionService;

@RestController
@RequestMapping(path = "/api/sesiones")
public class SesionRestController {

	private static final Logger log = LoggerFactory.getLogger(SesionRestController.class);

	@Autowired
	private SesionService sesionService;

	/**
	 * Recibe el archivo CSV y lo manda al service para cargar la base de datos a
	 * traves de el
	 *
	 * @param file Archivo CSV
	 * @return true si la carga fue exitosa false en caso contrario
	 */
	@PostMapping(path = "/loadFromCSV")
	public String loadFromCSV(@RequestParam MultipartFile file) {
		log.info(this.getClass().getSimpleName() + " loadFromCSV: mandar archivo CSV a SesionService");

		return "{\"ok\": " + this.sesionService.loadFromCSV(file) + "}";
	}
}