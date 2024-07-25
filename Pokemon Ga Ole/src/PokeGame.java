import java.util.*;
import javax.swing.*;
import java.awt.*;
public class PokeGame {
	
	ArrayList<Pokemon> Ocean = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Volcano = new ArrayList<Pokemon>();
	ArrayList<Pokemon> Forest = new ArrayList<Pokemon>();
	Account[] a;
	Displayer d = new Displayer();
	Scanner input = new Scanner(System.in);
	Account currPlayer, enemyPlayer;
	Database db = new Database();
	
	public final static int COMPUTER_MIN_ATK          = 30;
	public final static int COMPUTER_MAX_ATK          = 50;
	public final static int DEFAULT_HP                = 1000;
	public final static int DEFAULT_ATK               = 200;
	public final static int DEFAULT_DEF               = 50;
	
	public PokeGame() {
		a = new Account[2];
		a[0] = new Account(0); // player
		a[1] = new Account(1); //computer		
	}
	
	   /* start the game */   
	public void start() {
		//loop for login          battle              continue to battle
		boolean loginLoop = true, gameRunning = true, optionLoop = true, winStatus;

		while (loginLoop) {  //login
			try {
				int loginNum = d.PrintLogin(a[0], a[1]);
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
		d.PrintCatch(a[0], a[1], d.PrintSelectLocation(a[0], a[1], Ocean, Forest, Volcano));//select location and catch 
		d.PrintSelectPoke(a[0], a[1]);//select 2 pokemon to fight
		
		while(gameRunning) {
			Battle();// battle
			winStatus = CheckWinner();
			if(winStatus == true) {
				System.out.println("You are the winner!");
				System.out.println("Time to catch enemy's fallen pokemon!");
				a[0].resetFlip();
				d.PrintCatch(a[0],  a[1], a[1].GetDeadDisk());
				if(a[0].getCaughtP()!=null) {
					a[0].resetPoke(a[0].getCaughtP());
					d.PrintDisk(a[0].getCaughtP());
				}
			}else {
				System.out.println("You have lost!");
				a[0].setCaughtP(null);
			}
			
			d.PrintManageScores(a, a[0].getScore(), db); //manage scores 
			optionLoop = true;
			while(optionLoop) {
				System.out.println("1. Continue to battle ");
				System.out.println("2. Exit");
				System.out.println("NOTE: If you continue and you won you will continue with catched fallen pokemon, otherwise default pokemons!");
				System.out.println("      Previous catched pokemon will be removed!");
				try {
					System.out.print("Your option: ");
				int op = input.nextInt();
					if(op == 1) {
						manageContinueBattle();
						initializePokemon();
						d.PrintSelectPoke(a[0], a[1]);//select 2 pokemon to fight
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
	public void Battle() {
		int flip = (int)(Math.random() * 2)+1;
		if(flip == 1) {
			setCurrPlayer(a[1], a[0]);
		}else if(flip == 2) {
			setCurrPlayer(a[0], a[1]);
		}
		
		while(IsBattle(currPlayer,enemyPlayer)) {
			System.out.println("Your pokemon: ");
			for( int k = 0; k < a[0].GetOnFieldDisk().size(); k++) {
				d.PrintDisk(a[0].GetOnFieldDisk().get(k));
			}
			System.out.println("Your opponent(s) pokemon: ");
			for( int k = 0; k < a[1].GetOnFieldDisk().size(); k++) {
				d.PrintDisk(a[1].GetOnFieldDisk().get(k));
			}
			
			if(this.currPlayer == a[0]) {  //player
				boolean atkLoop = true;
				int pokeOp = -1;
				while(atkLoop) {
					try {
						System.out.print("Select Pokemon to attack [1 or 2]: ");
						pokeOp = input.nextInt();
						currPlayer.GetOnFieldP(pokeOp-1);
						atkLoop = false;
					}catch(Exception e) {
						System.out.println("ERROR!: " + e.getMessage());
						System.out.println("NOTE: If game doesnt continue key in anything and press enter!");
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

				for(int i = 0; i < enemyPlayer.GetOnFieldDisk().size(); i++ ) {
					currPlayer.attackVal(counter.getKeyVal(), currPlayer.GetOnFieldDisk().get(pokeOp-1), enemyPlayer.GetOnFieldDisk().get(i), enemyPlayer);
					System.out.println("Your total damage on " + enemyPlayer.GetOnFieldDisk().get(i).getName() + 
							": " + currPlayer.GetOnFieldDisk().get(pokeOp-1).getDamageDealt());
					currPlayer.setScore(currPlayer.getScore() + currPlayer.GetOnFieldDisk().get(pokeOp-1).getDamageDealt());
				}
				System.out.println("Your current score: " + currPlayer.getScore());
				int j = (enemyPlayer.GetOnFieldDisk().size()) -1;
					
				while(j>=0) {
					if(j<enemyPlayer.GetOnFieldDisk().size()){
						if(enemyPlayer.GetOnFieldDisk().get(j).getStatus() == false) {
							System.out.println(enemyPlayer.GetOnFieldDisk().get(j).getName() + " has been eliminated!");
							enemyPlayer.deleteOnFieldP(j);
							j = (enemyPlayer.GetOnFieldDisk().size()) -1;
						}else {
							j--;
						}
					}
				}
				
			}else if(this.currPlayer == a[1]) {  //computer 

				System.out.println("Enemy's turn to attack: ");
				int enemyAtker = (int)(Math.random() * currPlayer.GetOnFieldDisk().size());
				int enemyAtkVal = (int)(Math.random() * ((COMPUTER_MAX_ATK - COMPUTER_MIN_ATK) + 1) + COMPUTER_MIN_ATK);
				System.out.println("Enemy's input damage: " + enemyAtkVal);
				for(int i = 0; i < enemyPlayer.GetOnFieldDisk().size(); i++) {
					currPlayer.attackVal(enemyAtkVal, currPlayer.GetOnFieldDisk().get(enemyAtker), enemyPlayer.GetOnFieldDisk().get(i), enemyPlayer);
					System.out.println("Enemy's total damage on " + enemyPlayer.GetOnFieldDisk().get(i).getName() + 
							": " + currPlayer.GetOnFieldDisk().get(enemyAtker).getDamageDealt());
					currPlayer.setScore(currPlayer.getScore() + currPlayer.GetOnFieldDisk().get(enemyAtker).getDamageDealt());
				}
				System.out.println("Enemy current score: " + currPlayer.getScore());
				int j = (enemyPlayer.GetOnFieldDisk().size()) -1;
					
				while(j>=0) {
					if(j<enemyPlayer.GetOnFieldDisk().size()){
						if(enemyPlayer.GetOnFieldDisk().get(j).getStatus() == false) {
							System.out.println(enemyPlayer.GetOnFieldDisk().get(j).getName() + " has been eliminated!");
							enemyPlayer.addDeadPoke(enemyPlayer.GetOnFieldDisk().get(j));  //add into dead poke for catching later
							enemyPlayer.deleteOnFieldP(j);								
							j = (enemyPlayer.GetOnFieldDisk().size()) -1;
						}else {
							j--;
						}
					}
						
				}
			}
			SwitchPlayer();
		}
		
	}
	

	
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
	
	   /* chosen pokemon attack all enemy pokemon 
	    * If they do, update the arraylist and txt file 
	    * @param p1 - attacker pokemon
	    * @param p2 - enemy's player  */ 
	public void manageContinueBattle() {
		System.out.println(a[0].GetInventoryDisk().size());
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
	public void Attack(Pokemon p1, Account p2) {
		ArrayList<Pokemon> inventory = p2.GetInventoryDisk();
		for( int i = 0; i < inventory.size(); i++) {
			p1.attackVal(100,inventory.get(i));
		}
	}
	
	   /* Displayer all player's pokemon in their inventory
	    * @param a           - the account */   
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
	
	public boolean IsBattle(Account currPlayer, Account enemyPlayer) {
		if(currPlayer.GetOnFieldDisk().size() == 0 || enemyPlayer.GetOnFieldDisk().size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean CheckWinner() {
		if( a[1].GetOnFieldDisk().size() == 0) {
			return true;
		}
		return false;
	}
	
	
}
