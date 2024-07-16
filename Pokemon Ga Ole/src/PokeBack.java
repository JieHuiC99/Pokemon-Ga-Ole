import java.util.*;
public class PokeGame {
	
//	public final static int LOCATION_FOREST          = 1;
//	public final static int VOLCANO                  = 2;
//	public final static int ICEFALL_CAVE             = 3;
//	public final static int WATER_LABYRINTH          = 4;
//	Pokemon LocationP1, LocationP2, LocationP3;
	
	
	Pokemon[][] grid;
	ArrayList<Pokemon> Ocean = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Volcano = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Forest = new ArrayList<Pokemon>();
		
	Account player = new Account(0);
	Account npc = new Account(1);
	Displayer d = new Displayer();
	Scanner input = new Scanner(System.in);
	
	
//	private void GenerateGrid() {
//		int randomRow, randomCol;
//		
//		for( int i = 0; i < 1; i++) {
//			do {
//				randomRow = (int)(Math.random() * grid.length);
//	            randomCol = (int)(Math.random() * grid[0].length);
//			}while (grid[randomRow][randomCol] == new Charmander(1, 1000, 100, "FIRE", 1, 3));
//		}
//	}
	
	
//	private Pokemon GenerateFirePokemon() {
//		Pokemon[] p = new Pokemon[3];
//		int rand = (int)(Math.random() * 3);
//		p[0] = new FirePokemon("Charmander 1","FIRE",1000,1000,500);
//		p[1] = new FirePokemon("Charmander 2","FIRE",1000,1000,500);
//		p[2] = new FirePokemon("Charmander 3","FIRE",1000,1000,500);
//		return p[rand];
//	}
	
	
	
	public PokeGame() {
		grid = new Pokemon[3][3];
//		Location.add(LocationP1);
//		Location.add(LocationP2);
//		Location.add(LocationP3);
		//Pokemon pikachu = new ElectricPokemon("Pikachu", "ELECTRIC", 1000,1000,500);
		
		
	}
	
	public void start() {
		
		
		
		Account a1 = new Account(0);
		Account a2 = new Account(1);

		
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
					
					if(a1.IsLoginValid(playerID, playerPass) == true) {  //successfully logged in --------------------
						ArrayList<Pokemon> pInventory = a1.GetDisk();
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
		
	}  //===================================================================================================================
	
	public boolean login() {
		return false; //dummy value
	}
	
	public void GeneratePokemon(int op) {
		
	}
	
	public Pokemon[][] GetGrid(){
		return this.grid;
	}
	
	public int Attack(Pokemon p1, Account p2) {
		ArrayList<Pokemon> inventory = p2.GetDisk();
		for( int i = 0; i < inventory.size(); i++) {
			p1.attackVal(100,inventory.get(i));
		}
		return 0;
	}
	
	public void DisplayAllPlayerDisk(Account a) {
		ArrayList<Pokemon> inventory = a.GetDisk();
		for( int i = 0; i < inventory.size(); i++) {
			d.PrintDisk(inventory.get(i));
		}
	}
	
	
	public void SwtichPlayer() {
		
	}
	
}
