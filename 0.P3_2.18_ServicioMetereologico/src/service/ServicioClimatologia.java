/**
 * 
 */
package service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;


import model.RegistroDatos;
import util.Temperatura;
import model.TemperaturaHora;

/**
 * @author crbel
 *
 */
public class ServicioClimatologia {

	List<RegistroDatos> datos;
	

	public ServicioClimatologia(List<RegistroDatos> datos) {
		this.datos = datos;
	}
	
	/*
	 * METODO QUE DEVUELVE LAS TEMPERATURAS MAXIMAS AGRUPADAS POR PROVINCIA
	 */
	public Map<String, Optional<TemperaturaHora>> temperaturaMaximaProvincias(){
		return temperaturaMaxOMinProvincias(Temperatura.MAXIMA);
	}
	
	
	/*
	 * METODO QUE DEVUELVE LAS TEMPERATURAS MINIMAS AGRUPADAS POR PROVINCIA
	 */
	
	public Map<String, Optional<TemperaturaHora>> temperaturaMinimaProvincias(){
		return temperaturaMaxOMinProvincias(Temperatura.MINIMA);
	}
	
	
	/*
	 * METODO AUXILIAR, QUE UTILIZAN LAS DOS ANTERIORES, QUE SIRVE PARA REALIZAR
	 * EL AGRUPAMIENTO, SEGUN SI SE ESCOGE MAXIMO O MINIMO.
	 */
	
	private Map<String, Optional<TemperaturaHora>> temperaturaMaxOMinProvincias (Temperatura tipo){
		//utilizamos el operador ternario para construir el collector.
			Collector<RegistroDatos, ?, Optional<TemperaturaHora>> c = (tipo == Temperatura.MAXIMA)
							? Collectors.mapping(RegistroDatos::getMaxima,
											Collectors.maxBy((m1, m2) -> Float.compare(m1.getTemperatura(), m2.getTemperatura())))
							: Collectors.mapping(RegistroDatos :: getMinima,
											Collectors.minBy((m1, m2) -> Float.compare(m1.getTemperatura(), m2.getTemperatura())));
		//usamos el colector para agrupar
			return datos.stream().collect(Collectors.groupingBy(RegistroDatos::getProvincia, c));
	}
	
	
	/*
	 * METODO QUE CALCULA LAS TEMPERATURAS MEDIAS AGRUPADAS POR PROVINCIA
	 */
	public Map<String, Double> temperaturaMediaProvincias(){
		
		return datos.stream()
						.collect(Collectors.toMap(Function.identity(),
									(RegistroDatos r)-> (r.getMaxima().getTemperatura()+ r.getMinima().getTemperatura())/2))
						.entrySet().stream()
						.collect(Collectors.groupingBy((Map.Entry<RegistroDatos, Float> r)-> r.getKey().getProvincia(),
									TreeMap::new,
									Collectors.averagingDouble(((Map.Entry<RegistroDatos, Float> r) -> r.getValue()))));
	}
	
	
	
	/*
	 * Método que filtra los datos para obtener los de una provincia en particular
	 */
	public List<RegistroDatos> datosProvincia(String provincia) {

		return datos.stream()
						.filter(p -> p.getProvincia()
						.equalsIgnoreCase(provincia))
						.collect(Collectors.toList());
	}
	

	
	/*
	 * METODO QUE OBTIENE LA TEMPERATURA MAXIMA TOTAL
	 */
	public Optional<RegistroDatos> maximaTotal(){
		return datos.stream()
						.max((r1, r2)-> Float.compare(r1.getMaxima().getTemperatura(), r2.getMaxima().getTemperatura()));
	}
	
	
	
	/*
	 * METODO QUE OBTIENE LA TEMPERATURA MINIMA TOTAL
	 */
	public Optional<RegistroDatos> minimaTotal(){
		return datos.stream()
						.max((r1, r2)-> Float.compare(r1.getMinima().getTemperatura(), r2.getMinima().getTemperatura()));
	}
}