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

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	@Override
	public String toString() {
		return "Furgoneta [longitud=" + longitud + ", matricula=" + matricula + ", marca=" + marca + ", fechaEntrada="
				+ fechaEntrada + ", minutos=" + minutos + ", numPlazaAparcamiento=" + numPlazaAparcamiento + "]";
	}

	@Override
	public float calcularImporte() {
		return super.calcularImporte() + (Parking.PRECIO_POR_METRO * longitud);
	}
	
	

	
	
	

}
