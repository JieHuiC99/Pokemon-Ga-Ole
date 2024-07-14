import java.util.*;

public class PokeMain {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		PokeBack p = new PokeBack();
		Displayer d = new Displayer();
		Account a = new Account();
		ArrayList<Pokemon> pInventory = new ArrayList<Pokemon>();  //inventory for player's pokemon
		ArrayList<Pokemon> eInventory = new ArrayList<Pokemon>(); // Inventory for enemy's pokemon
		
		//dummy
		//Pokemon p1 = new Charmander(10000,10000,10000,4);
		//enemy test
		Pokemon enemyP = new WaterPokemon("Squirtle", "Water", 10000, 10000, 1000);
		eInventory.add(enemyP);
		
		
		boolean running = true;
		
		
		while(running) {
			try {				
				
				System.out.println("1. Login");
				System.out.println("2. Exit");
				System.out.print("Option: ");
				int op = input.nextInt();
				
				if(op == 1) {
					
					//Login---------------------------------------------------------					
					System.out.print("Enter PlayerID: ");
					int playerID = input.nextInt();	
					input.nextLine();
					System.out.print("Enter Password: ");					
					String playerPass = input.nextLine();
					
					if(a.IsLoginValid(playerID, playerPass) == true) {  //successfully logged in --------------------
						System.out.println("Succesfully Logged In!");
						System.out.println("Here is your inventory!");
						System.out.println(pInventory.size());
						
						for( int i = 0; i < pInventory.size(); i++ ) {
							d.PrintDisk(pInventory.get(i));
						}
						
						System.out.println("Location: Volcano");
						
						//d.PrintDisk(p1);
						
					}else {  //log in invalid -------------------------------------------------
						System.out.println("Your ID or Password is invalid!");
					}
					
				}
			}catch(Exception e) {
				if(e instanceof InputMismatchException	) {
					System.out.println("ERROR!: Invalid Input");	
					input.next();
				}else {
					//System.out.println("ERROR!: An error occured!");
					System.out.println(e.getMessage());
				}
				
			}
		}
		
		
		
	}
}
