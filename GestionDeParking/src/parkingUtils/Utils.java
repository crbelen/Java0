/**
 * 
 */
package parkingUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * @author crbel
 *
 */
public class Utils {

	/*Este metodo nos devuelve una instancia de LocalDateTime aleatoria,
	 * anterior a la fecha y hora actual, pero no muy lejana (en el intervalo de entre
	 * 2 y 6 horas)
	 */
	public static LocalDateTime fechaYHoraAleatoriaAlrededorFechaYHoraActual() {
		Random r = new Random();
		return LocalDateTime.now().minusHours(r.nextInt(4)+2).minusMinutes(r.nextInt(60)).minusSeconds(r.nextInt(60));
	}
	/*
	 * Nos devuelve la duracion en minutos entre dos fechas
	 */
	public static int minutosEntreDosFechas(LocalDateTime anterior, LocalDateTime posterior) {
		//Le sumamos un minuto a posterior, ya que al usar el metodo between, la fecha mas
		//reciente es exclusiva
		posterior = posterior.plusMinutes(1L);
		//Devolvemos la diferencia entre ambas fechas en minutos
		return (int) ChronoUnit.MINUTES.between(anterior, posterior);
	}
	
	public static int[]posicionNumeroPlaza(int n){
		
		int[]result;
		
		int unidades = n % 10;
		int decenas = n / 10;
		int fila, columna;
		
		//este calculo tenemos que hacerlo por la forma en que 
		//hemos dibujado las plazas en el parking
		//Si n%10 == 0
		//	la decena es impar -> [9] [decena-1]
		//	la decena es par -> [0] [decena-1]
		//si no
		// 		si la decena es par
		//			[unidad-1[decena]
		//		si no
		//			[10-unidad][decena]
		if (n % 10 == 0) {
			if (decenas % 10 == 1)
				fila = 9;
			else 
				fila = 0;
			columna = decenas - 1;
		} else {
			if (decenas % 2 == 0) 
				fila = unidades -1;
			else
				fila = 10 - unidades;
			columna = decenas;
		}
		
		// devolvemos las posiciones del resultado
		result = new int[]{fila, columna};
		
		return result;
		
	}
}


