import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Miguel Angel Osorio Sanchez
 *
 */
public class Ascensor {

	public static int TOPFLOOR = 29;
	//Array of floors
	private static ArrayList<Integer> stops = new ArrayList<Integer>();
	//Floor where elevator begins
	private static int startingPoint = 0;
	//Map of floors entered
	private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	//Map of console input
	private static Map<Integer, String> control = new HashMap<Integer, String>();
	//Elevator goes upwards or don't
	private static boolean isupwards = false;


	public Ascensor() {
		super();
	}
	


	public static void main(String[] args) throws IOException {
		
		
		int contador = 0;
		
		InputStreamReader entrada = new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader (entrada);
		String cadena = "";
		System.out.println("Introduzca las entradas: ");
		

		try {
//			cadena = input.readLine();
//			System.out.println("\n");

			while((cadena = input.readLine()) != null) {
			contador++;
			
			cadena = cadena.replace("[","");
			cadena = cadena.replace("]","");
			cadena = cadena.replace(",","");
			cadena = cadena.replace("{","");
			cadena = cadena.replace("}","");

//			System.out.println(contador);
			System.out.println(cadena);
			
			control.put(contador,cadena);
			
//			cadena = input.readLine();
			if(contador >=3) {
				break;
			}
			}
			
			input.close();
			entrada.close();

			
			assignValues(control);
			isupwards = isUpwards(startingPoint, stops);
			elevatorPath();
		
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}catch	(NumberFormatException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());

		}finally {
			input.close();
			entrada.close();
		}
		


        
    }


/**
 * This method assigns values to each of the global variables depending on the map 
 * created previously of the user input * 
 * @param control2
 */
	private static void assignValues(Map<Integer, String> control2) {
		String[] floors = splitting(control2.get(1), " ");
		String[] initial = splitting(control2.get(2), " ");
		String[] input = splitting(control2.get(3), " ");

		//Assigning values for floors entered
		for (int i = 0; i < input.length; i++) {
			String[] output = splitting(input[i], ":");
			try {
				int key = Integer.parseInt(output[0]);
				int value = Integer.parseInt(output[1]);

				map.put(key, value);
			}catch (Exception e) {
				System.out.println("Exception: "+e.getMessage());
			}
		}
		
		//Assigning values for stops of the elevator
		for (int i = 0; i < floors.length; i++) {
			try {
				int value = Integer.parseInt(floors[i]);
				stops.add(value);
			}catch(NumberFormatException e) {
				System.out.println("Exception: "+e.getMessage());
			}
		}
		
		//Assigning 
		try {
			int value = Integer.parseInt(initial[0]);
			startingPoint = value;
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		

	}
	
	/**
	 * 
	 * @param arr - String to be splitted
	 * @param splitter - Splitter to be used 
	 * @return
	 */
	private static String[] splitting(String arr, String splitter) {
		String[] splitted = arr.split(splitter);
		return splitted;
	}
	
	/**
	 * This method returns true if the elevator must begin going upwards
	 * @param initial
	 * @param floors
	 * @return
	 */
	private static boolean isUpwards(int initial, ArrayList<Integer> floors) {
		boolean upwards = false;
		
		if(initial < floors.get(0)) {
			upwards = true;
		}else if (initial > floors.get(0)){
			upwards = false;
		}else if (initial == floors.get(0)){
			if(initial < floors.get(1)) {
				upwards = true;
			}else if (initial > floors.get(1)){
				upwards = false;
			}
		}
		System.out.println(isupwards);
		isupwards = upwards;
		System.out.println(isupwards);

		return upwards;
	}
	
	//This method prints the elevator path
	public static void elevatorPath() {
		//If elevator is going upwards this var is 1, otherwise, it will be -1
		int counting = 1;
		int currentPosition = startingPoint;
		
		String msjPiso = "Elevador en piso ";
		String msjDetiene = "Elevador se detiene → ";
		String msjSubiendo = "Elevador subiendo ";
		String msjBajando = "Elevador descendiendo ";
		String msjPisoIngresado = "Piso ingresado → ";

		for (int i = 0; i < stops.size(); i++) {
			boolean found = false;
			while((currentPosition <= TOPFLOOR) || (currentPosition > 0) || !found) {
				
				System.out.println(msjPiso + currentPosition);
				
				if(isupwards) {
					System.out.println(msjSubiendo);
				}else {
					System.out.println(msjBajando);
				}
				
				// Elevator actually reaches a floor of the list
				if(currentPosition==stops.get(i)) {
					found = true;
					//TODO - Sacar el piso de la lista de paradas
					System.out.println(msjDetiene + "");
					
					//Busca el piso con la clave currentPosition, si ese piso no está en stops
					// lo debe agregar a la lista de stops
					if(floorEntered(stops, currentPosition)) {
						System.out.println(msjPisoIngresado);
					}
					
					break;
				}

				currentPosition += counting;

				if(currentPosition == 0) {
					counting = 1;
					isupwards = true;
				}else if(currentPosition > TOPFLOOR) {
					counting = -1;
					currentPosition += counting -1;
					isupwards = false;
				}
				
			}
			
			
		}
	}



	private static boolean floorEntered(ArrayList<Integer> stops2, int currentPosition) {
		// TODO Auto-generated method stub
		return true;
	}
}
