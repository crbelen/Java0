/**
 * 
 */
package io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.RegistroDatos;
import model.TemperaturaHora;

/**
 * @author crbel
 *
 */
public class MeteoReader {
//readDataOfPath recibe la ruta y la fecha
public static Optional<List<RegistroDatos>> readDataOfPath(Path p, LocalDate fecha) {
		//primero comprobamos si ese fichero existe y si es asi, lo procesamos
		if (Files.exists(p)) {			
			try (Stream<String> stream = Files.lines(p, Charset.forName("Cp1252"))) {
				return Optional.of(stream
			        .map(s -> s.split(";"))//con split transformamos obteniendo un array de cadenas de caracteres
//peek es un metodo especial que nos sirve para depurar el trabajo con strings			        
			        .peek(splitted -> System.out.println(Arrays.toString(splitted)))
			        .map(splitted -> {
			        	String estacion = splitted[0];
			        	String provincia = splitted[1];
			        	TemperaturaHora maxima = new TemperaturaHora(Float.parseFloat(splitted[2]), LocalTime.parse(splitted[3], DateTimeFormatter.ofPattern("H:mm")));
			        	TemperaturaHora minima = new TemperaturaHora(Float.parseFloat(splitted[4]), LocalTime.parse(splitted[5], DateTimeFormatter.ofPattern("H:mm")));
			        	float precipitaciones = Float.parseFloat(splitted[6]);
			        	return new RegistroDatos(fecha, estacion, provincia, maxima, minima, precipitaciones);
			        })
			        .collect(Collectors.toList()));
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
				return Optional.empty();//para evitar que se salga del programa devolvemos un opcional vacio.
			} 
			//si el fichero no existe devolvemos
		} else {
			return Optional.empty();
		}
 }
}

