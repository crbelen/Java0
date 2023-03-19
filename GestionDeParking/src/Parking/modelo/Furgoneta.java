/**
 * 
 */
package Parking.modelo;

import java.time.LocalDateTime;

/**
 * @author crbel
 *
 */
public class Furgoneta extends Vehiculo {
	


	private float longitud;

	public Furgoneta() {
	}
	
	public Furgoneta(String matricula, String marca, float longitud) {
		super(matricula, marca);
		this.longitud = longitud;
	}
	
	

	
	
	

}
