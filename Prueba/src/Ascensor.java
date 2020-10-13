import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
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

			while((cadena = input.readLine()) != null) {
			contador++;
			
			cadena = cadena.replace("[","");
			cadena = cadena.replace("]","");
			cadena = cadena.replace(",","");
			cadena = cadena.replace("{","");
			cadena = cadena.replace("}","");
			
			control.put(contador,cadena);
			
			if(contador >=3) {
				break;
			}
			}
			
			input.close();
			entrada.close();

			System.out.println("\n");
			
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
		
		isupwards = upwards;

		return upwards;
	}
	
	/**
	 * This method prints the elevator path
	 */
	public static void elevatorPath() {
		//If elevator is going upwards this var is 1, otherwise, it will be -1
		int counting = 1;
		int currentPosition = startingPoint;
		int counter = 0;
		
		String msjPiso = "Elevador en piso ";
		String msjDetiene = "Elevador se detiene";
		String msjSubiendo = "Elevador subiendo ";
		String msjBajando = "Elevador descendiendo ";
		String msjPisoIngresado = "Piso ingresado → ";

		for (int i = 0; i <= stops.size(); i++) {
			boolean found = false;	
			//indicates if the current position is greater than the index
			boolean minor = false;

			while((currentPosition <= TOPFLOOR) || (currentPosition > 0) || !found || !minor) {
				
				ArrayList<Integer> stopsClon = (ArrayList<Integer>) stops.clone();
				ArrayList<Integer> stopsOrdered = stopsOrdered(stopsClon);
				
				counter++;
				System.out.println(counter+". "+msjPiso + currentPosition);
				
				// The elevator goes upwards
				if(isupwards) {
					if(stops.size()==1) {
						i=0;
					}
					if(currentPosition > stopsOrdered.get(i)) {
						counter++;
						System.out.println(counter+". "+msjSubiendo);
						minor = true;
						break;
					}else if (currentPosition < stopsOrdered.get(i)){
						counter++;
						System.out.println(counter+". "+msjSubiendo);

					}else if(currentPosition==stopsOrdered.get(i)) {
						// Elevator actually reaches a floor of the list
						found = true;
						int index = stops.indexOf(currentPosition);
						stops.remove(index);
						i -= 1;
						
						if(!stops.isEmpty()) {
							counter++;
							System.out.println(counter+". "+msjDetiene + " → " + printStops(stops));							
						}else {
							counter++;
							System.out.println(counter+". "+msjDetiene);
						}
						
						//Busca el piso con la clave currentPosition, si ese piso no está en stops
						// lo debe agregar a la lista de stops
						if(floorEntered(stops, currentPosition)) {
							counter++;
							System.out.println(counter+". "+msjPisoIngresado + printStops(stops));
						}
						
						if(currentPosition==TOPFLOOR) {
							if(!stops.isEmpty()) {
								counter++;
								System.out.println(counter+". "+msjBajando);
							}
						}
						else if(!stops.isEmpty()) {
							counter++;
							System.out.println(counter+". "+msjSubiendo);
						}
						
						break;
					}
				}
				// The elevator goes downwards
				else {
					if(stops.size()==1) {
						i=0;
					}
					if(currentPosition > stopsOrdered.get(i)) {
						counter++;
						System.out.println(counter+". "+msjBajando);

					}else if (currentPosition < stopsOrdered.get(i)){
						counter++;
						System.out.println(counter+". "+msjBajando);
						minor = true;
						break;
						
					}else if(currentPosition==stopsOrdered.get(i)) {
						// Elevator actually reaches a floor of the list
						found = true;
						int index = stops.indexOf(currentPosition);
						stops.remove(index);
						i -= 1;
						
						if(!stops.isEmpty()) {
							counter++;
							System.out.println(counter+". "+msjDetiene + " → " + printStops(stops));							
						}else {
							counter++;
							System.out.println(counter+". "+msjDetiene);
						}
						
						//Busca el piso con la clave currentPosition, si ese piso no está en stops
						// lo debe agregar a la lista de stops
						if(floorEntered(stops, currentPosition)) {
							counter++;
							System.out.println(counter+". "+msjPisoIngresado + printStops(stops));
						}
						
						if(currentPosition==0) {
							if(!stops.isEmpty()) {
								counter++;
								System.out.println(counter+". "+msjSubiendo);
							}
						}else if(!stops.isEmpty()) {
							counter++;
							System.out.println(counter+". "+msjBajando);
						}
						
						break;
					}
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
			
			currentPosition += counting;

			if(currentPosition == 0) {
				counting = 1;
				isupwards = true;
			}else if(currentPosition > TOPFLOOR) {
				counting = -1;
				currentPosition += counting -1;
				isupwards = false;
			}
			
			if(i==stops.size()-1) {
				i=0;
			}
			
		}
	}


	/**
	 * Returns true if the current stop's destiny has not been entered already to the stops list
	 * @param stops2
	 * @param currentPosition
	 * @return
	 */
	private static boolean floorEntered(ArrayList<Integer> stops2, int currentPosition) {
		boolean exists = false;
		if(!stops2.isEmpty()) {
			for (int i = 0; i < stops2.size(); i++) {
				if( map.get(currentPosition) == stops2.get(i)) {
					exists = true;
				}
			}
			if(exists==true) {
				return false;
			}else {
				if(map.get(currentPosition) != null) {
					stops.add(map.get(currentPosition));
					return true;
				}else {
					return false;
				}
			}
		}else {
			return false;
		}
			
	}
	
	/**
	 * Formats the output of stops on console
	 * @param stops2
	 * @return
	 */
	private static String printStops(ArrayList<Integer> stops2) {
		String msg = "";
		
		msg = msg+"[";

		for (int i = 0; i < stops2.size(); i++) {
			if(i==stops2.size()-1) {
				msg = msg+(stops2.get(i));
			}else {
				msg = msg+(stops2.get(i))+",";
			}
		}
		msg = msg+"]";
		
		return msg;
	}
	
	/**
	 * Orders with sort method the arraylist coming as a parameter
	 * @param arrayListInt
	 * @return
	 */
	private static ArrayList<Integer> stopsOrdered (ArrayList<Integer> arrayListInt){
		ArrayList<Integer> ordered = arrayListInt;
		Collections.sort(ordered);
		return ordered;
	}
}
