/**
 * 
 */
package app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.MeteoReader;
import model.RegistroDatos;
import service.ServicioClimatologia;
import util.PrintUtil;
import model.TemperaturaHora;

/**
 * @author crbel
 *
 */
public class App {

	private static ServicioClimatologia servicio;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//comprobamos si nuestra app ha recibido argumentos con este if.
		//si ha recibido args, construimos un List de pequeños arrays de cadenas de caracteres
		//donde se almacenen los pares(fecha-ruta de fichero).
		//
		if (args.length >= 2 && (args.length % 2 == 0)) {
			List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(args.length/2)//como va por pares, la cantidad de pasos a dar es el array entre 2.
					.mapToObj(n -> new String[] { args[n], args[n + 1] }).collect(Collectors.toList());
//vamos a recorrer el string de pares, de forma que para cada pareja obtenemos la fecha parseandola con formato conocido y
			//por otro lado la ruta para poder leer los datos.
			pares.stream().forEach(pair -> {
				String fecha = pair[0];
				LocalDate ld = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				Path path = Paths.get(pair[1]);
				MeteoReader.readDataOfPath(path, ld).ifPresent((list) -> {
					servicio = new ServicioClimatologia(list);
					imprimirInformeMeteorologico(ld.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
				});
			});
		//si no hubiese recibido los argumentos porque el num de args sea <2 o no sea par(clave-valor) indicaremos
		//en el else un error de sintaxis.
		} else {
			System.err.println(
					"Error de sintaxis: java -jar meteo.jar dd-mm-yyyy fichero1.csv [dd-mm-yyyy fichero2.csv ...]");
		}
//proporcionamos los argumentos en configuracion (run as/run configuration/app/argumentos)de los tres archivos csv que tenemos en files
	}
	private static void imprimirInformeMeteorologico(String fecha) {

		System.out.printf("DATOS METEOROLÓGICOS DEL DÍA %s%n", fecha.toUpperCase());
		System.out.println("**************************************************\n\n");
		maximaYMinimaTotal();
		maximaPorProvincias();
		minimaPorProvincias();
		mediasPorProvincias();
		datosPorProvincia("SEVILLA");
		System.out.println("\n\n");

	}

	private static void maximaPorProvincias() {
		System.out.println("TEMPERATURA MÁXIMA POR PROVINCIAS");
		System.out.println("-----------------------------------------------------");
		servicio = null;
		Map<String, Optional<TemperaturaHora>> maximas = servicio.temperaturaMaximaProvincias();

		PrintUtil.printMapProvinciaTemperaturaHora(maximas);
	}

	private static void minimaPorProvincias() {
		System.out.println("TEMPERATURA MÍNIMA POR PROVINCIAS");
		System.out.println("-----------------------------------------------------");
		Map<String, Optional<TemperaturaHora>> minimas = servicio.temperaturaMinimaProvincias();

		PrintUtil.printMapProvinciaTemperaturaHora(minimas);

	}

	private static void mediasPorProvincias() {
		System.out.println("TEMPERATURA MEDIA POR PROVINCIAS");
		System.out.println("-----------------------------------------------------");
		Map<String, Double> medias = servicio.temperaturaMediaProvincias();

		medias.forEach(
				(provincia, media) -> System.out.printf("Provincia: %-25s | Media: %5.2f°C%n", provincia, media));
		System.out.println("");
	}

	private static void datosPorProvincia(String provincia) {
		System.out.println("DATOS DE LA PROVINCIA DE " + provincia.toUpperCase());
		System.out.println("-----------------------------------------------------");

		List<RegistroDatos> datos = servicio.datosProvincia(provincia);
		// Otra forma de imprimir una serie de datos de forma ordenada
		datos.stream().sorted(Comparator.comparing(RegistroDatos::getEstacionMeteorologica))
				.forEach(r -> System.out.printf(
						"Estación meteorológica: %-35s | Máxima: % 6.2f (%s) | Mínima: % 6.2f (%s) | Precipitación: %.2f%n",
						r.getEstacionMeteorologica(), r.getMaxima().getTemperatura(), r.getMaxima().getHora(),
						r.getMinima().getTemperatura(), r.getMinima().getHora(), r.getPrecipitacion()));

	}

	private static void maximaYMinimaTotal() {

		System.out.println("TEMPERATURAS MÁXIMA Y MÍNIMA DE ESPAÑA");
		System.out.println("-----------------------------------------------------");

		Optional<RegistroDatos> maxima, minima;

		maxima = servicio.maximaTotal();
		minima = servicio.minimaTotal();

		// Nos aseveramos bien que el valor de Optional está presente
		if (maxima.isPresent())
			System.out.printf("MÁXIMA -> Estación meteorológica: %s (%s) | Temperatura: %.2f°C | Hora: %s%n",
					maxima.get().getEstacionMeteorologica(), maxima.get().getProvincia(),
					maxima.get().getMaxima().getTemperatura(), maxima.get().getMaxima().getHora());
		else
			System.out.println("No tenemos datos sobre la temperatura máxima");

		if (minima.isPresent())
			System.out.printf("MÍNIMA -> Estación meteorológica: %s (%s) | Temperatura: %.2f°C | Hora: %s%n",
					minima.get().getEstacionMeteorologica(), minima.get().getProvincia(),
					minima.get().getMinima().getTemperatura(), minima.get().getMinima().getHora());
		else
			System.out.println("No tenemos datos sobre la temperatura máxima");

		System.out.println("");

	}

}
