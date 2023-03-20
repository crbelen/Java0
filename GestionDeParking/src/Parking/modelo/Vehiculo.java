/**
 * 
 */
package Parking.modelo;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author crbel
 *
 */
public class Vehiculo {

	protected String matricula;
	protected String marca;
	protected LocalDateTime fechaEntrada;
	protected int minutos;
	protected int numPlazaAparcamiento;
	
	
	public Vehiculo() {
	}
	

	public Vehiculo(String matricula) {
		this.matricula = matricula;
	}


	public Vehiculo(String matricula, String marca) {
		this.matricula = matricula;
		this.marca = marca;
	}


	public Vehiculo(String matricula, String marca, LocalDateTime fechaEntrada, int minutos, int numPlazaAparcamiento) {
		this.matricula = matricula;
		this.marca = marca;
		this.fechaEntrada = fechaEntrada;
		this.minutos = minutos;
		this.numPlazaAparcamiento = numPlazaAparcamiento;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}


	public void setFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}


	public int getMinutos() {
		return minutos;
	}


	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}


	public int getNumPlazaAparcamiento() {
		return numPlazaAparcamiento;
	}


	public void setNumPlazaAparcamiento(int numPlazaAparcamiento) {
		this.numPlazaAparcamiento = numPlazaAparcamiento;
	}
	
	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", fechaEntrada=" + fechaEntrada + ", minutos="
				+ minutos + ", numPlazaAparcamiento=" + numPlazaAparcamiento + "]";
	}

	
	public float calcularImporte() {
		return Parking.PRECIO_BASE_POR_MINUTO * minutos;
	}


	@Override
	public int hashCode() {
		final int prime= 31;
		int result = 1;
		result = prime * result + ((fechaEntrada == null) ? 0: fechaEntrada.hashCode());
		result = prime * result + ((marca == null)? 0 : marca.hashCode());
		result = prime * result + ((matricula == null)? 0 : matricula.hashCode());
		result = prime * result + minutos;		
		return result;
	}
	
	
	//Modificamos la implementacion por defecto del metodo equals para
	//identificar que dos vehiculos seran iguales si lo es su matricula
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Vehiculo other = (Vehiculo) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		}else if(!matricula.equalsIgnoreCase(other.matricula))
			return false;
		return true;
	}


	
	

}
