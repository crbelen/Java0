/**
 * APLICACION QUE IMPLEMENTA LA GESTION DE UN PARKING
 */
package parkingp;


import java.util.Scanner;


import Parking.modelo.Autobus;
import Parking.modelo.Furgoneta;
import Parking.modelo.Parking;
import Parking.modelo.Vehiculo;

/**
 * @author crbel
 *
 */
public class App {
	
	static Parking parking;
	
	static Scanner sc;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Inicializamos las clases necesarias
		sc = new Scanner(System.in);
		//Ponemos en marcha el Parking.
		parking = new Parking();
		
		int opcion = 0;
		
		do {
			// Imprimimos el menú
			menu();
			System.out.print("Introduzca la opción seleccionada: ");
			opcion = Integer.parseInt(sc.nextLine());
			System.out.println("");
			switch (opcion) {
			case 1:
				registrarEntradaVehiculo();
				parking.imprimirEstadoParking();
				break;
			case 2:
				registrarSalidaVehiculo();
				parking.imprimirEstadoParking();
				break;
			case 3:
				if (parking.getPlazasDisponibles() > 0) {
					System.out.printf("Hay %d plazas disponibles %n", parking.getPlazasDisponibles());
				} else {
					System.out.println("El parking está COMPLETO");
				}
				break;
			case 4:
				parking.imprimirEstadoParking();
				break;
			case 5:
				System.out.printf("El saldo acumulado es de %.2f", parking.getSaldoAcumulado());
				break;
			case 6:
				parking.getVehiculos().forEach(System.out::println);
				break;
			default:
				System.out.println("Introduzca una opción correcta");
			}
			
		

		} while (opcion != 0);

		sc.close();
	}
	public static void menu() {

		System.out.println("BIENVENIDO AL PARKING OPENWEBINARS");
		System.out.println("==================================\n");
		System.out.println("0. Salir del programa");
		System.out.println("1. Registrar la entrada de un vehículo");
		System.out.println("2. Registrar la salida de un vehículo");
		System.out.println("3. Número de plazas disponibles");
		System.out.println("4. Imprimir estado del parking");
		System.out.println("5. Saldo acumulado del día");
		System.out.println("6. Imprimir la lista de vehiculos que hay en el parking");

	}



	public static void registrarEntradaVehiculo() {
		//Identificamos el tipo de vehiculo
		int opcion = 0;
		do {
			System.out.println("1. Coche o moto");
			System.out.println("2. Furgoneta");
			System.out.println("3. Autobus");
			System.out.println("Introduzca el tipo de vehiculo: ");
			
			opcion = Integer.parseInt(sc.nextLine());
		} while(opcion <0 ||opcion > 3) ;
		
		//Recogemos los datos propios de cualquier vehiculo
			System.out.println("Introduzca la marca del vehiculo: ");
			String marca = sc.nextLine();
			System.out.println("Introduzca la matricula del vehiculo: ");
			String matricula = sc.nextLine();
			
			Vehiculo v = null;
			
			//En funcion del tipo de vehiculo, creamos una u otra referencia
			switch (opcion) {
			case 1: 
				//Almacenamos los datos en mayusculas
				v = new Vehiculo(matricula.toUpperCase(), marca.toUpperCase());
				break;
			case 2:
				//Si es una furgoneta, solicitamos la longitud
				System.out.println("Introduzca la longitud en metros de la furgoneta (puede incluir decimales): ");
				float longitud = Float.parseFloat(sc.nextLine());
				v = new Furgoneta(matricula, marca, longitud);
				break;
			case 3:
				//Si es un autobus, solicitamos el numero de plazas
				System.out.println("Introduzca el numero de plazas del autobus: ");
				int numPlazas = Integer.parseInt(sc.nextLine());
				v = new Autobus(matricula, marca, numPlazas);
			}
			
			//Registramos la entrada del vehiculo
			parking.registraEntradaVehiculo(v);
			System.out.println("");	
	}
	public static void registrarSalidaVehiculo() {
		System.out.println("\nIntroduzca la matricula del vehiculo: ");
		String matricula = sc.nextLine();
		//para registrar la salida de un vehiculo solamente
		//necesitamos su matricula
		parking.registrarSalidaVehiculo(new Vehiculo(matricula));
	}
	
}
