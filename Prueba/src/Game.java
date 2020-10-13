import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Miguel Ángel Osorio Sánchez
 *
 */
public class Game {

	private int dimX;
	private int dimY;
	private Integer[][] matrix;
	private Map<Integer, Integer> snakes;
	private Map<Integer, Integer> ladders;
	int startingPoint;
	int dice;

	public Game() {
		super();
		dimX = 5;
		dimY = 5;
		matrix = new Integer[dimX][dimY];
		snakes = new HashMap<Integer, Integer>();
		ladders = new HashMap<Integer, Integer>();

		snakes.put(14,4);
		snakes.put(19,8);
		snakes.put(22,20);
		snakes.put(24,16);

		ladders.put(3,11);
		ladders.put(10,12);
		ladders.put(9,18);
		ladders.put(6,17);

		startingPoint = 0;
		dice = 0;
	}

	/**
	 * This main method plays automatically the game "Escaleras y Serpientes" and it ends when reaching the top value of the board
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Game game = new Game();
		int startingP = game.startingPoint = 0;
		int dice = game.dice = 0;
		boolean win = false;
		int currentPosition = startingP;

		int counter = 0;
		
		try {
			System.out.println ("Por favor introduzca la cantidad de filas que tendrá el tablero");

	        String filas = "";
	        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
	        filas = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
	        System.out.println ("Entrada recibida por teclado es: \"" + filas +"\"");
	  
			System.out.println ("Por favor introduzca la cantidad de filas que tendrá el tablero");

			String columnas = entradaEscaner.nextLine ();
	        System.out.println ("Entrada recibida por teclado es: \"" + columnas +"\""+"\n");

	        entradaEscaner.close();
	        System.out.println("\n");
	        
	        game.dimX = Integer.parseInt(columnas);
	        game.dimY = Integer.parseInt(filas);
	        
	        if(Integer.parseInt(columnas)<0 || Integer.parseInt(filas)<0) {
	        	throw new NumberFormatException();
	        }
	        
		
		
		// GAME LOGIC
			while(!win) {
	
				dice = (int) Math.floor(Math.random()*6+1);
				
				counter++;
				System.out.println(counter+". "+"Dado arroja: "+ dice);
	
				currentPosition += dice;
				counter++;
				System.out.println(counter+". "+"Jugador avanza a cuadro: "+ currentPosition);
				
	
				//Reaches a snake's mouth and player is sent back
				if(game.snakes.get(currentPosition) != null){
					currentPosition = game.snakes.get(currentPosition);
					counter++;
					System.out.println(counter+". "+"Jugador desciende al cuadro: " + currentPosition);
				}
	
				//Reaches a ladder's bottom and player is sent forward
				if(game.ladders.get(currentPosition) != null){
					currentPosition = game.ladders.get(currentPosition);
					counter++;
					System.out.println(counter+". "+"Jugador sube por escalera al cuadro: " + currentPosition);

				}
	
				int a = game.dimX * game.dimY;
				if(currentPosition >= a) {
					win = true;
					counter++;
					System.out.println(counter+". "+"Fin");
					
				}
	
			}
		}catch(NumberFormatException e) {
			System.out.println("Solo se permiten valores numéricos enteros positivos. Vuelva a ejecutar el juego");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}




}
