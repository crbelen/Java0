package util;

import java.util.Map;
import java.util.Optional;//contenedor que evita nullpointexception

import model.TemperaturaHora;

public class PrintUtil {

	public static void printMapProvinciaTemperaturaHora(Map<String, Optional<TemperaturaHora>> map) {
		
		//una forma de imprimir un map de forma ordenada
		map.keySet().stream().sorted().forEach(provincia -> {
				TemperaturaHora t = map.get(provincia).get();
				System.out.printf("Provincia: %-25s | Temperatura: % 6.2fºC | Hora: %s%n", provincia, t.getTemperatura(),
						t.getHora().toString());
		});
		System.out.println("");
	}
}