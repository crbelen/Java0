package Parking.modelo;

public class Autobus extends Vehiculo{

	private int numPlazas;

	public Autobus() {
		
	}

	public Autobus(String matricula, String marca, int numPlazas) {
		super(matricula, marca);
		this.numPlazas = numPlazas;
	}

	public int getNumPlazas() {
		return numPlazas;
	}

	public void setNumPlazas(int numPlazas) {
		this.numPlazas = numPlazas;
	}

	@Override
	public String toString() {
		return "Autobus [numPlazas=" + numPlazas + ", matricula=" + matricula + ", marca=" + marca + ", fechaEntrada="
				+ fechaEntrada + ", minutos=" + minutos + ", numPlazaAparcamiento=" + numPlazaAparcamiento + "]";
	}

	@Override
	public float calcularImporte() {
		return super.calcularImporte() + (Parking.PRECIO_POR_ASIENTO * numPlazas);
	}
	
	
	
	
}
