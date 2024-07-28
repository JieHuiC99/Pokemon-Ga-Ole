import java.util.*;
public class PokeGame {
	
	public final static int COMPUTER_MIN_ATK          = 30;
	public final static int COMPUTER_MAX_ATK          = 50;
	public final static int DEFAULT_HP                = 1000;
	public final static int DEFAULT_ATK               = 200;
	public final static int DEFAULT_DEF               = 50;
	
	
	Scanner input = new Scanner(System.in);
	private ArrayList<Pokemon> Ocean = new ArrayList<Pokemon>();
	private ArrayList<Pokemon> Volcano = new ArrayList<Pokemon>();
	private ArrayList<Pokemon> Forest = new ArrayList<Pokemon>();
	private Account[] a;
	private Displayer d = new Displayer();
	private Account currPlayer, enemyPlayer;
	Database db = new Database();
	
	
	/*=============== CONSTRUCTOR ===============*/
	public PokeGame() {
		a = new Account[2];
		a[0] = new Account("", "", true); // player
		a[1] = new Account("comp", "compPass", false); //computer		
//		db.importScores();
	}
	
	/*=============== PRIVATE METHOD ===============*/
	private void attack(int atkVal, int atker) {
		for(int i = 0; i < enemyPlayer.getOnFieldDisk().size(); i++) {
			currPlayer.attackVal(atkVal, currPlayer.getOnFieldDisk().get(atker), enemyPlayer.getOnFieldDisk().get(i), enemyPlayer);
			System.out.println(currPlayer.getPlayerRole() + "'s total damage on " + enemyPlayer.getOnFieldDisk().get(i).getName() + 
					": " + currPlayer.getOnFieldDisk().get(atker).getDamageDealt());
			currPlayer.setScore(currPlayer.getScore() + currPlayer.getOnFieldDisk().get(atker).getDamageDealt());
		}
		System.out.println( currPlayer.getPlayerRole() + " current score: " + currPlayer.getScore());
		int j = (enemyPlayer.getOnFieldDisk().size()) -1;
			
		while(j>=0) {
			if(j<enemyPlayer.getOnFieldDisk().size()){
				if(enemyPlayer.getOnFieldDisk().get(j).getStatus() == false) {
					System.out.println(enemyPlayer.getOnFieldDisk().get(j).getName() + " has been eliminated!");
					enemyPlayer.addDeadPoke(enemyPlayer.getOnFieldDisk().get(j));  //add into dead poke for catching later
					enemyPlayer.deleteOnFieldP(j);								
					j = (enemyPlayer.getOnFieldDisk().size()) -1;
				}else {
					j--;
				}
			}
				
		}
	}
	
	//=========== PUBLIC METHOD ===========//	
	   /* start the game */   
	public void start() {
		//loop for login          battle              continue to battle
		boolean loginLoop = true, gameRunning = true, optionLoop = true, winStatus;

		while (loginLoop) {  //login
			try {
				int loginNum = d.printLogin(a[0], a[1]);
				if( loginNum == 1 ) {
					
					loginLoop = false;
				}else if(loginNum == 0) {
					return;
				}
			}catch(Exception e) {
				if(e instanceof InputMismatchException) {
					System.out.println("ERROR!: Please input integer for option and ID and String for pass!");
					input.nextLine();
				}else {
					System.out.println("ERROR!: An error occured!");
				}
			}
		}
		
		initializePokemon();
		d.printCatch(a[0], a[1], d.printSelectLocation(a[0], a[1], Ocean, Forest, Volcano));//select location and catch 
		d.printSelectPoke(a[0], a[1]);//select 2 pokemon to fight
		
		while(gameRunning) {
			battle();// battle
			winStatus = checkWinner();
			if(winStatus == true) {
				System.out.println("You are the winner!");
				System.out.println("Time to catch enemy's fallen Pokemon!");
				a[0].resetFlip();
				d.printCatch(a[0],  a[1], a[1].getDeadDisk());
				if(a[0].getCaughtP()!=null) {
					a[0].resetPoke(a[0].getCaughtP());
					d.printDisk(a[0].getCaughtP());
				}
			}else {
				System.out.println("You have lost!");
				a[0].setCaughtP(null);
			}
			
			d.printManageScores(a, a[0].getScore(), db); //manage scores 
			optionLoop = true;
			while(optionLoop) {
				System.out.println("1. Continue to battle ");
				System.out.println("2. Exit");
				System.out.println("NOTE: If you continue after winning this round, you will continue with the fallen Pokemon caught, otherwise default Pokemons!");
				System.out.println("      Previously caught Pokemon will be removed!");
				try {
					System.out.print("Your option: ");
					int op = input.nextInt();
					if(op == 1) {
						for(int i = 0; i < a.length; i++) {
							for(int j = 0; j< a[i].getInventoryDisk().size(); j++) {
								a[i].resetPoke(a[i].getInventoryDisk().get(j));
								a[i].resetFlip();
							}
						}
						manageContinueBattle();
						initializePokemon();
						d.printSelectPoke(a[0], a[1]);//select 2 pokemon to fight
						optionLoop = false;
					}else if(op == 2) {
						System.out.println("Thank you for playing!");
						gameRunning = false;
						optionLoop = false;
					}
				}catch(Exception e) {
					System.out.println("ERROR! INPUT 1 OR 2!");
					input.next();
				}
			}
			
		}
		
		
		
		
	}  //===================================================================================================================
	
	   /* Battle
	    * Randomize to decide if player or computer start first
	    * When player's turn, gui will pop up for player to spam a key
	    * When computer's turn, computer will randomize a   */   
	public void battle() {
		int flip = (int)(Math.random() * 2)+1;
		if(flip == 1) {
			setCurrPlayer(a[1], a[0]);
		}else if(flip == 2) {
			setCurrPlayer(a[0], a[1]);
		}
		
		while(isBattle(currPlayer,enemyPlayer)) {
			System.out.println("Your Pokemon: ");
			for( int k = 0; k < a[0].getOnFieldDisk().size(); k++) {
				d.printDisk(a[0].getOnFieldDisk().get(k));
			}
			System.out.println("Your opponent's Pokemon(s): ");
			for( int k = 0; k < a[1].getOnFieldDisk().size(); k++) {
				d.printDisk(a[1].getOnFieldDisk().get(k));
			}
			
			if(this.currPlayer == a[0]) {  //player
				boolean atkLoop = true;
				int pokeOp = -1;
				while(atkLoop) {
					try {
						System.out.print("Select Pokemon to attack [1 or 2]: ");
						pokeOp = input.nextInt();
						currPlayer.getOnFieldP(pokeOp-1);
						atkLoop = false;
					}catch(Exception e) {
						System.out.println("ERROR!: " + e.getMessage());
						System.out.println("NOTE: If game doesn't continue, key in anything and press enter!");
						input.next();
					}
				}
				
				KeySpam counter = new KeySpam();
			    counter.setVisible(true);
			    counter.setAlwaysOnTop(true);
			    System.out.println("NOTE: Score will NOT be counted IF you press enter before the timer ends!");
			    System.out.print("Press enter to continue...");
				input.nextLine();
				input.nextLine();
				System.out.println("Your input damage: " + counter.getKeyVal());
				attack(counter.getKeyVal(), pokeOp-1 );
				
			}else if(this.currPlayer == a[1]) {  //computer 
				System.out.println("Enemy's turn to attack: ");
				int enemyAtker = (int)(Math.random() * currPlayer.getOnFieldDisk().size());
				int enemyAtkVal = (int)(Math.random() * ((COMPUTER_MAX_ATK - COMPUTER_MIN_ATK) + 1) + COMPUTER_MIN_ATK);
				System.out.println("Enemy's input damage: " + enemyAtkVal);
				attack(enemyAtkVal, enemyAtker );
			}
			switchPlayer();
		}
		
	}
	
	   /* initialize the pokemon and put them into separate locations
	    * water element goes to Ocean, fire goes to Volcano and electric goes to Forest  */ 
	public void initializePokemon() {
		
		Pokemon squirtle = new WaterPokemon("Squirtle","WATER",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon piplup = new WaterPokemon("Piplup","WATER",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon psyduck = new WaterPokemon("Psyduck","WATER",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Ocean.add(squirtle);
		Ocean.add(piplup);
		Ocean.add(psyduck);
		a[1].addPoke(Ocean);
		
		Pokemon ninetales = new FirePokemon("Ninetales","FIRE",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon vulpix = new FirePokemon("Vulpix", "FIRE", PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon ponyta = new FirePokemon("Ponyta", "FIRE", PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF); 
		Volcano.add(ninetales);
		Volcano.add(vulpix);
		Volcano.add(ponyta);
		a[1].addPoke(Volcano);
		
		Pokemon jolteon = new ElectricPokemon("Jolteon","ELECTRIC",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon voltorb = new ElectricPokemon("Voltorb","ELECTRIC",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon magnemite = new ElectricPokemon("Magnemite","ELECTRIC",PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Forest.add(jolteon);
		Forest.add(voltorb);
		Forest.add(magnemite);
		a[1].addPoke(Forest);
	}
	
	   /* if player chose to continue battle, reset all player's inventory 
	    * re-add default pokemon into their inventory (pikachu and charmander)   */ 
	public void manageContinueBattle() {
		for(int i = 0; i < a.length; i++) {
			a[i].reset();
		}
		Pokemon charmander = new FirePokemon("Charmander", "Fire", PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		Pokemon pikachu = new ElectricPokemon("Pikachu", "Electric", PokeGame.DEFAULT_HP,PokeGame.DEFAULT_ATK,PokeGame.DEFAULT_DEF);
		a[0].addPoke(charmander);
		a[0].addPoke(pikachu);
		if(a[0].getCaughtP() != null) {
			a[0].addPoke(a[0].getCaughtP());
		}
	}
	
	   /* chosen pokemon attack all enemy pokemon 
	    * If they do, update the arraylist and txt file 
	    * @param p1 - attacker pokemon
	    * @param p2 - enemy's player  */   
	public void attack(Pokemon p1, Account p2) {
		ArrayList<Pokemon> inventory = p2.getInventoryDisk();
		for( int i = 0; i < inventory.size(); i++) {
			p1.attackVal(100,inventory.get(i));
		}
	}
	
	   /* Displayer all player's pokemon in their inventory
	    * @param a           - the account */   
	public void displayAllPlayerDisk(Account a) {
		ArrayList<Pokemon> inventory = a.getInventoryDisk();
		for( int i = 0; i < inventory.size(); i++) {
			d.printDisk(inventory.get(i));
		}
	}
	
	   /* switch player's turn */ 
	public void switchPlayer() {
		if(this.currPlayer == a[0]) {
			this.currPlayer = a[1];
			this.enemyPlayer = a[0];
		}else if(this.currPlayer == a[1]) {
			this.currPlayer = a[0];
			this.enemyPlayer = a[1];
		}
	}
	
	   /* get the current player
	    * @return   - current player  */ 
	public Account getCurrPlayer() {
		return this.currPlayer;
	}
	
	   /* set current player and enemy player 
	    * @param currPlayer  - current player
	    * @param enemyPlayer - enemy player  */ 
	public void setCurrPlayer(Account currPlayer, Account enemyPlayer) {
		this.currPlayer = currPlayer;
		this.enemyPlayer = enemyPlayer;
	}
	
	   /* check if the battle is ongoing   
	    * @param currPlayer  - current player
	    * @param enemyPlayer - enemy player
	    * @return            - true if battle is ongoing (no winner yet)
	    *                    - false if there is a winner  */ 
	public boolean isBattle(Account currPlayer, Account enemyPlayer) {
		if(currPlayer.getOnFieldDisk().size() == 0 || enemyPlayer.getOnFieldDisk().size() == 0) {
			return false;
		}
		return true;
	}
	
	   /* check who is the winner
	    * @return   - true if winner is the player
	    *           - false if the winner is the computer/enemey  */ 
	public boolean checkWinner() {
		if( a[1].getOnFieldDisk().size() == 0) {
			return true;
		}
		return false;
	}
	
	
}
