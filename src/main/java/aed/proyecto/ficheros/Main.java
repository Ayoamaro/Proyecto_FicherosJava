package aed.proyecto.ficheros;

import java.util.Scanner;

/**
 * @author Ayoze Amaro
 *
 */
public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static boolean salir = false;
	
	public static void main(String[] args) {

		while (salir == false) {
			System.out.println("¡Bienvenido al Proyecto Ficheros de Java!");
			menuPrincipal();
		}
	}
	
	private static void menuPrincipal() {
		try {
			System.out.println("");
			System.out.println("OPCIONES DISPONIBLES");
			System.out.println("1. Indicar la existencia de un Fichero o Directorio");
			System.out.println("2. Crear Fichero");
			System.out.println("3. Crear Directorio");
			System.out.println("4. Borrar Fichero");
			System.out.println("5. Borrar Directorio");
			System.out.println("6. Renombrar Fichero");
			System.out.println("7. Visualizar Fichero");
			System.out.println("8. Modificar Fichero");
			System.out.println("9. Visualizar Ruta y Nombre de un Fichero");
			System.out.println("10. Salir");
			System.out.print("Opción: ");
			String select = sc.nextLine();
			System.out.println("");

			switch (select) {
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					break;
				case "6":
					break;
				case "7":
					break;
				case "8":
					break;
				case "9":
					break;
				case "10":
					salir = true;
					System.out.println("");
					System.out.println("¡ADIÓS!");
					break;
				default:
					salir = true;
					System.out.println("");
					System.out.println("¡ADIÓS!");
					break;
			}
		} catch(Exception ex) {
			System.out.println("ERROR: " + ex);
		}
	}

}
