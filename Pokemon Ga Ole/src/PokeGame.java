import java.util.*;
public class PokeGame {
	
//	public final static int LOCATION_FOREST          = 1;
//	public final static int VOLCANO                  = 2;
//	public final static int ICEFALL_CAVE             = 3;
//	public final static int WATER_LABYRINTH          = 4;
//	Pokemon LocationP1, LocationP2, LocationP3;
	
	
	
	ArrayList<Pokemon> Ocean = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Volcano = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Forest = new ArrayList<Pokemon>();
	Account[] a;
//	Account player = new Account(0);
//	Account npc = new Account(1);
	Displayer d = new Displayer();
	Scanner input = new Scanner(System.in);
	Account currPlayer;
	
	
	
	
	public PokeGame() {
		a = new Account[2];
		a[0] = new Account(0); // player
		a[1] = new Account(1); //computer
//		Location.add(LocationP1);
//		Location.add(LocationP2);
//		Location.add(LocationP3);
		//Pokemon pikachu = new ElectricPokemon("Pikachu", "ELECTRIC", 1000,1000,500);
		
		
	}
	
	public void start() {
		
		boolean loginLoop = true, catchLoop = true;
		
		while (loginLoop) {
			try {
				if( login() == true ) {
					loginLoop = false;
				}
			}catch(Exception e) {
				if(e instanceof InputMismatchException) {
					System.out.println("ERROR!: Please input integer for option and ID and String for pass!");
					input.next();
				}else {
					System.out.println("ERROR!: An error occured!");
				}
			}
		}
		
		Catch(SelectLocation());
		
		//SelectLocation();
		

		
	}  //===================================================================================================================
	
	public boolean login() {
		System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Option: ");
        int option = input.nextInt();
        
        switch (option) {
            case 1: //login
                System.out.print("Enter PlayerID: ");
                int playerID = input.nextInt();    
                input.nextLine(); 
                System.out.print("Enter Password: ");
                String password = input.nextLine();
                
                if (a[0].IsLoginValid(playerID, password)) {  
                    System.out.println("Successfully Logged In!");
                    System.out.printf("You have %d default Pokemon in your inventory:\n", a[0].GetDisk().size());
                    for (Pokemon pokemon : a[0].GetDisk()) {
                        d.PrintDisk(pokemon);
                    }
                    return true;                            
                } else {  // login invalid
                    System.out.println("Your ID or Password is invalid!");
                    return false;
                }
                //break;
            
            case 2: // Exit
                System.out.println("Exiting the game...");
                return false;

            default:
                System.out.println("Invalid option! Please select 1 or 2.");
                return false;
        }
	}
	
	public ArrayList<Pokemon> SelectLocation(){

		ArrayList<Pokemon> pReturn = new ArrayList<Pokemon>();
		
		
		boolean locationLoop = true;
		while(locationLoop) {
			try {
				System.out.println("Choose a location!");
				System.out.println("1. Ocean");
				System.out.println("2. Volcano");
				System.out.println("3. Forest");
				System.out.print("Option: ");
				int location = input.nextInt();
				
				switch(location) {
				case 1:
					System.out.println("Ocean - Squirtle, Piplup, Psyduck");
					Pokemon squirtle = new WaterPokemon("Squirtle","WATER",1000,1000,500);
					Pokemon piplup = new WaterPokemon("Piplup","WATER",1000,1000,500);
					Pokemon psyduck = new WaterPokemon("Psyduck","WATER",1000,1000,500);
					Ocean.add(squirtle);
					Ocean.add(piplup);
					Ocean.add(psyduck);
					pReturn = Ocean;
					locationLoop = false;
					break;
					
				case 2:
					System.out.println("Volcano - Charmander, Vulpix, Ponyta");
					Pokemon charmander = new FirePokemon("Charmander","FIRE",1000,1000,500);
					Pokemon vulpix = new FirePokemon("Vulpix", "FIRE", 1000,1000,500);
					Pokemon ponyta = new FirePokemon("Ponyta", "FIRE", 1000,1000,500); 
					Volcano.add(charmander);
					Volcano.add(vulpix);
					Volcano.add(ponyta);
					pReturn = Volcano;
					locationLoop = false;
					break;
					
				case 3:
					System.out.println("Forest - Pikachu, Voltorb, Magnemite");
					Pokemon pikachu = new ElectricPokemon("Pikachu","ELECTRIC",1000,1000,500);
					Pokemon voltorb = new ElectricPokemon("Voltorb","ELECTRIC",1000,1000,500);
					Pokemon magnemite = new ElectricPokemon("Magnemite","ELECTRIC",1000,1000,500);
					Forest.add(pikachu);
					Forest.add(voltorb);
					Forest.add(magnemite);
					pReturn = Forest;
					locationLoop = false;
					break;
				default:
					break;
				}
			}catch(Exception e) {
				System.out.println("akshdjksad");
				input.next();
			}
			
			}
			
		return pReturn;
		
		
		//return Ocean; 
	}
	
	public void Catch(ArrayList<Pokemon> fallenPokemon) {
		a[0].placePokeInGrid(fallenPokemon);  //place pokemon in grid to catch 
		boolean running = true;
		int chance =(int) (Math.random() * 3);
		
		System.out.print("Push button to get a poke ball! (Press enter to continue)...");		
		input.nextLine();
		//input.next();
		System.out.println("You got...");
		System.out.println(a[0].getPokeBall()[chance].toString());
		
		int i = 0;
		while(i < a[0].getPokeBall()[chance].getChance()) {
			try {
				d.PrintGrid(a[0].getGrid());
				System.out.print("x-Cord to move/flip: ");                        
		        int xPos = input.nextInt();
		        System.out.print("y-Cord to move/flip: ");
		        int yPos = input.nextInt();
		        int flip = a[0].flip(xPos, yPos);
		        if( flip == 0) {
		        	System.out.println("Oops, nothing is there! :(");
		        	i++;
		        }else if(flip == 1) {
		        	d.PrintGrid(a[0].getGrid());
		        	System.out.println("Congratulations! You have caught a pokemon!");
		        	a[0].addPoke(a[0].getGrid()[yPos-1][xPos-1]);		        	
		        	break;
		        }
			}catch(Exception e) {
				if( e instanceof InputMismatchException) {
					System.out.println("ERROR!: Please input a valid integer!");
					input.next();
				}else {
					System.out.println(e.getMessage());
					//System.out.println("ERROR!: An error occured!");
					//input.next();
				}
			}
		}
		
		
	}
	
	public void GeneratePokemon(int op) {
		
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

/*
Scanner input = new Scanner(System.in);
PokeGame game = new PokeGame(); 
Displayer displayer = new Displayer();
Account account = new Account(0);
ArrayList<Pokemon> playerInventory = new ArrayList<>();  
ArrayList<Pokemon> enemyInventory = new ArrayList<>(); 

//default pokemon in inventory
Pokemon charmander = new FirePokemon("Charmander", "Fire", 500, 50, 30);
Pokemon pikachu = new ElectricPokemon("Pikachu", "Electric", 500, 50, 30);
playerInventory.add(charmander);
playerInventory.add(pikachu);

// testing for enermy pokemon
Pokemon enemyP = new WaterPokemon("Squirtle", "Water", 10000, 10000, 1000);
enemyInventory.add(enemyP);

/////////////login//////////////////
boolean running = true;

while(running) {
    try {                
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Option: ");
        int option = input.nextInt();
        
        switch (option) {
            case 1: //login
                System.out.print("Enter PlayerID: ");
                int playerID = input.nextInt();    
                input.nextLine(); 
                System.out.print("Enter Password: ");
                String password = input.nextLine();
                
                if (account.IsLoginValid(playerID, password)) {  
                    System.out.println("Successfully Logged In!");
                    System.out.printf("You have %d default Pokemon in your inventory:\n", playerInventory.size());
                    for (Pokemon pokemon : playerInventory) {
                        displayer.PrintDisk(pokemon);
                    }
                    //return true;                            
                } else {  // login invalid
                    System.out.println("Your ID or Password is invalid!");
                }
                break;
            
            case 2: // Exit
                System.out.println("Exiting the game...");
                running = false;
                break;

            default:
                System.out.println("Invalid option! Please select 1 or 2.");
                break;
        }
    } catch (InputMismatchException ime) {
        System.out.println("ERROR: Invalid input! Please enter a number.");
        input.next();
    } catch (Exception e) {
        System.out.println("ERROR: An unexpected error occurred: " + e.getMessage());
    }
}

input.close();
*/
