import java.util.*;
public class PokeGame {
	
	ArrayList<Pokemon> Ocean = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Volcano = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Forest = new ArrayList<Pokemon>();
	Account[] a;
	Displayer d = new Displayer();
	Scanner input = new Scanner(System.in);
	Account currPlayer, enemyPlayer;
	public final static int COMPUTER_MIN_ATK          = 30;
	public final static int COMPUTER_MAX_ATK          = 50;
	
	public PokeGame() {
		a = new Account[2];
		a[0] = new Account(0); // player
		a[1] = new Account(1); //computer		
	}
	
	public void start() {
		
		boolean loginLoop = true;
		
		//login
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
		
		//select location and catch 
		Catch(SelectLocation());
		
		SelectPoke(a[0], a[1]);
		
		Battle();
		
	}  //===================================================================================================================
	
	public boolean login() {
		
		Pokemon charmander = new FirePokemon("Charmander", "Fire", 1000,200,50);
		Pokemon pikachu = new ElectricPokemon("Pikachu", "Electric", 1000,200,50);
		a[0].addPoke(charmander);
		a[0].addPoke(pikachu);
		
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
                    System.out.printf("You have %d default Pokemon in your inventory:\n", a[0].GetInventoryDisk().size());
                    for (Pokemon pokemon : a[0].GetInventoryDisk()) {
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
		
		Pokemon squirtle = new WaterPokemon("Squirtle","WATER",1000,200,50);
		Pokemon piplup = new WaterPokemon("Piplup","WATER",1000,200,50);
		Pokemon psyduck = new WaterPokemon("Psyduck","WATER",1000,200,50);
		Ocean.add(squirtle);
		Ocean.add(piplup);
		Ocean.add(psyduck);
		a[1].addPoke(Ocean);
		
		Pokemon charmander = new FirePokemon("Charmander","FIRE",1000,200,50);
		Pokemon vulpix = new FirePokemon("Vulpix", "FIRE", 1000,200,50);
		Pokemon ponyta = new FirePokemon("Ponyta", "FIRE", 1000,200,50); 
		Volcano.add(charmander);
		Volcano.add(vulpix);
		Volcano.add(ponyta);
		a[1].addPoke(Volcano);
		
		Pokemon pikachu = new ElectricPokemon("Pikachu","ELECTRIC",1000,200,50);
		Pokemon voltorb = new ElectricPokemon("Voltorb","ELECTRIC",1000,200,50);
		Pokemon magnemite = new ElectricPokemon("Magnemite","ELECTRIC",1000,200,50);
		Forest.add(pikachu);
		Forest.add(voltorb);
		Forest.add(magnemite);
		a[1].addPoke(Forest);
		
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
					pReturn = Ocean;
					pokemonGUI("Ocean", Ocean);
					locationLoop = false;
					break;
					
				case 2:
					System.out.println("Volcano - Charmander, Vulpix, Ponyta");
					pReturn = Volcano;
					pokemonGUI("Volcano", Volcano);
					locationLoop = false;
					break;
					
				case 3:
					System.out.println("Forest - Pikachu, Voltorb, Magnemite");
					pReturn = Forest;
					pokemonGUI("Forest", Forest);
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

	}

	// GUI for Pokemon
	private void setTextPosition(JLabel label, int horizontalAlignment, int verticalAlignment) {
	    label.setHorizontalTextPosition(horizontalAlignment);
	    label.setVerticalTextPosition(verticalAlignment);
	}
	
	public void pokemonGUI(String title, ArrayList<Pokemon> pokemons)	{
		JFrame frame = new JFrame("Pokemon - " + title);
        frame.setSize(650, 275);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Pokemon pokemon : pokemons) {
            ImageIcon image = new ImageIcon(new ImageIcon(PokeGame.class.getResource(pokemon.getName() + ".png")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            label.setText(pokemon.getName());
            setTextPosition(label, JLabel.CENTER, JLabel.BOTTOM);
            frame.add(label);
        }
        
        frame.setVisible(true);
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
	
	public void SelectPoke(Account player, Account computer) {
		
		System.out.println("Here are your pokemons!");
		
		for( int i = 0; i < player.GetInventoryDisk().size(); i++) {
			System.out.println("Pokemon " + (i+1) + ":");
			d.PrintDisk(a[0].GetInventoryDisk().get(i));
		}
		System.out.println("Choose your pokemon to be on battle!");
		int i = 0, j = 0;
		while(i < 2) {
			try {
				System.out.print("Pokemon " + (i+1) + ": ");
				int pokeOption = input.nextInt();
				a[0].addOnFieldPoke(a[0].GetInventoryDisk().get(pokeOption-1));
				a[1].deleteInventoryP(a[0].GetInventoryDisk().get(pokeOption-1));
				i++;
			}catch(Exception e) {
				if(e instanceof InputMismatchException) {
					System.out.println("ERROR!: Please input integer for option and ID and String for pass!");
					input.next();
				}else {
					System.out.println("ERROR!: An error occured!");
				}
			}
		}
		System.out.println("asdasdadsasdad");
		
		while(j < 2) {
			int randPoke =(int) (Math.random() * a[1].GetInventoryDisk().size());
			a[1].addOnFieldPoke(a[1].GetInventoryDisk().get(randPoke));
			j++;
		}
		
		
//		a[1].addOnFieldPoke(a[1].GetInventoryDisk().get(0));
//		a[1].addOnFieldPoke(a[1].GetInventoryDisk().get(1));
		
		
	}
	
	public void Battle() {
		int flip = (int)(Math.random() * 2)+1;
		if(flip == 1) {
			setCurrPlayer(a[1], a[0]);
		}else if(flip == 2) {
			setCurrPlayer(a[0], a[1]);
		}
		
		while(IsGameRunning(currPlayer,enemyPlayer)) {
			
			System.out.println("Your pokemon: ");
			for( int k = 0; k < a[0].GetOnFieldDisk().size(); k++) {
				d.PrintDisk(a[0].GetOnFieldDisk().get(k));
			}
			
			System.out.println("Your opponent(s) pokemon: ");
			for( int k = 0; k < a[1].GetOnFieldDisk().size(); k++) {
				d.PrintDisk(a[1].GetOnFieldDisk().get(k));
			}
			
			if(this.currPlayer == a[0]) {  //player
				System.out.print("Select Pokemon to attack [1 or 2]: ");
				int pokeOp = input.nextInt();
				KeySpam counter = new KeySpam();
			    counter.setVisible(true);
			    System.out.println("NOTE: Score will NOT be counted IF you press enter before the timer ends!");
			    System.out.print("Press enter to continue...");
				input.nextLine();
				input.nextLine();
				System.out.println("Your input damage: " + counter.getKeyVal());

				for(int i = 0; i < enemyPlayer.GetOnFieldDisk().size(); i++ ) {
					currPlayer.attackVal(counter.getKeyVal(), currPlayer.GetOnFieldDisk().get(pokeOp-1), enemyPlayer.GetOnFieldDisk().get(i), enemyPlayer);
				}
				int j = (enemyPlayer.GetOnFieldDisk().size()) -1;
				
				while(j>=0) {
					if(j<enemyPlayer.GetOnFieldDisk().size()){
						if(enemyPlayer.GetOnFieldDisk().get(j).getStatus() == false) {
							enemyPlayer.deleteOnFieldP(j);
							j = (enemyPlayer.GetOnFieldDisk().size()) -1;
						}else {
							j--;
						}
					}
				}
				

				//SwitchPlayer();
			}else if(this.currPlayer == a[1]) {
				System.out.println("Enemy's turn to attack: ");
				int enemyAtker = (int)(Math.random() * currPlayer.GetOnFieldDisk().size());
				int enemyAtkVal = (int)(Math.random() * ((COMPUTER_MAX_ATK - COMPUTER_MIN_ATK) + 1) + COMPUTER_MIN_ATK);
				System.out.println("Enemy's input damage: " + enemyAtkVal);
				for(int i = 0; i < enemyPlayer.GetOnFieldDisk().size(); i++) {
					currPlayer.attackVal(enemyAtkVal, currPlayer.GetOnFieldDisk().get(enemyAtker), enemyPlayer.GetOnFieldDisk().get(i), enemyPlayer);
				}
				int j = (enemyPlayer.GetOnFieldDisk().size()) -1;
				
				while(j>=0) {
					if(j<enemyPlayer.GetOnFieldDisk().size()){
						if(enemyPlayer.GetOnFieldDisk().get(j).getStatus() == false) {
							enemyPlayer.deleteOnFieldP(j);
							j = (enemyPlayer.GetOnFieldDisk().size()) -1;
						}else {
							j--;
						}
					}
					
				}
				
				//SwitchPlayer();
			}
			SwitchPlayer();
		}
		
	}
	
	public void GeneratePokemon(int op) {
		
	}
	
	public int Attack(Pokemon p1, Account p2) {
		ArrayList<Pokemon> inventory = p2.GetInventoryDisk();
		for( int i = 0; i < inventory.size(); i++) {
			p1.attackVal(100,inventory.get(i));
		}
		return 0;
	}
	
	public void DisplayAllPlayerDisk(Account a) {
		ArrayList<Pokemon> inventory = a.GetInventoryDisk();
		for( int i = 0; i < inventory.size(); i++) {
			d.PrintDisk(inventory.get(i));
		}
	}
	
	
	public void SwitchPlayer() {
		if(this.currPlayer == a[0]) {
			this.currPlayer = a[1];
			this.enemyPlayer = a[0];
		}else if(this.currPlayer == a[1]) {
			this.currPlayer = a[0];
			this.enemyPlayer = a[1];
		}
	}
	
	public Account getCurrPlayer() {
		return this.currPlayer;
	}
	
	public void setCurrPlayer(Account currPlayer, Account enemyPlayer) {
		this.currPlayer = currPlayer;
		this.enemyPlayer = enemyPlayer;
	}
	
	public boolean IsGameRunning(Account currPlayer, Account enemyPlayer) {
		if(currPlayer.GetOnFieldDisk().size() == 0 || enemyPlayer.GetOnFieldDisk().size() == 0) {
			return false;
		}
		return true;
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
