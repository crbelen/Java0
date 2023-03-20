/**
 * Clase que modela el Parking y su logica de negocio
 */
package Parking.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import parkingUtils.Utils;

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
	public static final float PRECIO_POR_METRO = 0.2f;
	public static final float PRECIO_POR_ASIENTO = 0.25f;
	
	public Parking() {
		saldoAcumulado = 0.0f;
		plazasDisponibles = 100;
		vehiculos = new ArrayList<>();
		//nuestro parking es cuadrado, así que lo representamos en
		//un array bidimensional de 10*10
		plazasAparcamiento = new PlazaAparcamiento[10][10];
		int numPlaza = 0;
		for (int j = 0; j < 10; j++) {
			//las columnas pares se recorren de arriba a abajo
			//y las impares de abajo a arriba
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
		
		// 1		20		21		..	..	..	..	..	..	100
		// 2	|	19	.	22	|	..						99
		// 3	.	18	|	23	.
		// 4		17		24	
		// 5		16		25
		// 6		15		26
		// 7		14		27	
		// 8		13		28
		// 9		12		29	
		// 10		11		30		31	..	..	..	..	..	91
	
		}
	
	public int getPlazasDisponibles() {
		return plazasDisponibles;
		}
	public List<Vehiculo> getVehiculos(){
		return vehiculos;
		}

	public PlazaAparcamiento[][] getPlazasAparcamiento() {
		return plazasAparcamiento;
		}

	public float getSaldoAcumulado() {
		return saldoAcumulado;
		}
	/*
	 * METODO QUE IMPRIME, DE FORMA CONVENIENTE, EL MAPA DEL PARKING,
	 * INDICANDO TODAS LAS PLAZAS QUE HAY Y SI ESTAN OCUPADAS O NO
	 */
	public void imprimirEstadoParking() {
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				PlazaAparcamiento plaza = plazasAparcamiento[i][j];
				String strPlaza = String.format("%3s", Integer.toString(plaza.getNumero())) + 
						"" + ((plaza.isLibre())? "L" : "O") + " ";
						System.out.print(strPlaza);
			}
			System.out.println();
			
		}
		System.out.println("");
	}
	
	/*
	 * METODO QUE REGISTRA LA ENTRADA DE UN VEHICULO EN EL PARKING
	 */
		public void registraEntradaVehiculo(Vehiculo v) {
			
			if(plazasDisponibles > 0) {
				//asignamos la fecha y hora de entrada
				v.setFechaEntrada(Utils.fechaYHoraAleatoriaAlrededorFechaYHoraActual());
				
				//colocamos el coche en una plaza de aparcamiento
				//simularemos que esto se produce de forma aleatoria
				Random r = new Random();
				boolean cocheAparcado;
				int i, j, numPlaza = 0;
				
				do {
					cocheAparcado = false;
					i = r.nextInt(10);
					j = r.nextInt(10);
					
					if (plazasAparcamiento[i][j].isLibre()) {
						plazasAparcamiento[i][j].setLibre(false);
						cocheAparcado = true;
						numPlaza = plazasAparcamiento[i][j].getNumero();
					}
				}while(!cocheAparcado);
				
				//añadimos el coche a la lista de coches que tenemos
				//dentro del parking, asignandole el numero de plaza que ocupa
				v.setNumPlazaAparcamiento(numPlaza);
				vehiculos.add(v);
				--plazasDisponibles;
			}else {
				System.out.println("El parking esta completo\n");
			}
			
		}
		
		/*
		 * METODO QUE REGISTRA LA SALIDA DE UN VEHICULO
		 */
		public void registrarSalidaVehiculo(Vehiculo v) {
			
			if(!vehiculos.contains(v)) {
				System.out.println("Este vehiculo no esta en el parking");
				return;
			}else {
				//Rescatamos la instancia de vehiculo que tenemos
				//almacenada, ya que es la que tiene registrada
				//la fecha y hora de entrada.
				v = vehiculos.get(vehiculos.indexOf(v)); 
			}
			
			LocalDateTime salida = LocalDateTime.now();
			
			//Asignamos el numero de minutos para calcular el importe
			v.setMinutos(Utils.minutosEntreDosFechas(v.getFechaEntrada(), salida));
			
			//Imprimimos el mensaje con el importe de pago
			StringBuilder ticket = new StringBuilder(String.format("TICKET DE SALIDA: %nMatricula %s %nFecha y hora de llegada: %s " 
					+ "%nFecha y hora de salida: %s %nMinutos de estancia: %d%n",
					v.getMatricula(),
					v.getFechaEntrada().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
					v.getMinutos()));
			
			if(v instanceof Furgoneta) {
				Furgoneta furgo = (Furgoneta)v;
				ticket.append(String.format("Longitud de la ufrgoneta %.2f ", furgo.getLongitud()));
			}else if(v instanceof Autobus) {
				Autobus bus = (Autobus)v;
				ticket.append(String.format("Num. de plazas del autobus: %d " , bus.getNumPlazas()));
			}
			
			ticket.append(String.format("%nImporte total de la estancia: %.2f€ %n%n", v.calcularImporte()));
			
			System.out.println(ticket.toString());
			
			//Añadimos el importe al saldo acumulado
			saldoAcumulado += v.calcularImporte();
			
			//identificamos la posicion qeu tenia ocupada el coche para dejarla libre
			int[] coordenadas = Utils.posicionNumeroPlaza(v.getNumPlazaAparcamiento());
			plazasAparcamiento[coordenadas[0]][coordenadas[1]].setLibre(true);
			
			//eliminamos el vehiculo de la lista
			vehiculos.remove(v);
			
			++plazasDisponibles;
			
		}
	}
	

	
	

