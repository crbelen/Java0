/**
 * Clase que modela el Parking y su logica de negocio
 */
package Parking.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crbel
 *
 */
public class Parking {

	//Mantenemos una lista con los vehículos que hay dentro del parking
	private List<Vehiculo> vehiculos;
	
	//Ademas, necesitamos saber qué plazas están libres o cuales están ocupadas
	private PlazaAparcamiento[][] plazasAparcamiento;
	
	//El numero de plazas disponibes, para poder visualizarlo en el cartel de la entrada
	//Si el numeor de plazas disponibles es 0, el parking está COMPLETO
	private int plazasDisponibles;
	
	//Almacenamos el importe total que hemos cobrado a lo alrgo de la sesion
	private float saldoAcumulado;
	
	//la carta de precios la establecemos estaticamente en constantes, si bien
	//sería positivo buscar otro sistema, como un fichero de properties.
	public static final float PRECIO_BASE_POR_MINUTO = 0.04f;
	public static final float PRECIO_BASE_POR_METRO = 0.2f;
	public static final float PRECIO_BASE_POR_ASIENTO = 0.25f;
	
	public Parking() {
		saldoAcumulado = 0.0f;
		plazasDisponibles = 100;
		vehiculos = new ArrayList<>();
		//nuestro parking es cuadrado, así que lo representamos en
		//un array bidimensional de 10*10
		plazasAparcamiento = new PlazaAparcamiento[10][10];
		int numPlaza = 0;
		for (int j = 0; j < 10; j++) {
			//las columnas impares se recorren de arriba a abajo
			//y las pares de abajo a arriba
			if(j % 2 == 0)
				for (int i= 0; i < 10; i++)
					plazasAparcamiento[i][j] = new PlazaAparcamiento(++numPlaza);
			else
				for (int i= 9; i >= 0; i--)
					plazasAparcamiento[i][j] = new PlazaAparcamiento(++numPlaza);
		}
		
		//Recorremos asi el array para que quede de la siguiente forma
		//NOTA: Las flechas hacia abajo y arriba simulan el sentido de
		//los carriles por los que circularian los coches.
	}
	
}
