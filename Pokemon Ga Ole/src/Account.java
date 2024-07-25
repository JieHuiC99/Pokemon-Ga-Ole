import java.util.ArrayList;
public class Account {
	
	//Login details
	private int playerID;
	private String pass;
	private final static int accountPlayerID = 0001;
	private final static String accountPass = "p";
	
	//computer or player 
	private String playerRole;
	
	//player's items 
	private ArrayList<Pokemon> pInventory;
	private ArrayList<Pokemon> pOnField;
	private ArrayList<Pokemon> pDead;
	private Pokemon caughtP;
	private int score;
	PokeBall[] Pokeball;
	Pokemon[][] grid;
	
	public Account(int num) {
		
		if(num == 0) {
			this.playerRole = "PLAYER";
		}else if( num == 1) {
			this.playerRole = "ENEMY";
		}
		
		pInventory = new ArrayList<Pokemon>();
		pOnField = new ArrayList<Pokemon>();
		pDead = new ArrayList<Pokemon>();
		grid = new Pokemon[3][3];
		Pokeball = new PokeBall[3];
		Pokeball[0] = new PokePoke(2, "Poke Ball");
		Pokeball[1] = new PokeGreat(4, "Great Ball");
		Pokeball[2] = new PokeUltra(6, "Ultra Ball");
		score = 0;
	}
	
	public Pokemon[][] getGrid() {
		return this.grid;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void placePokeInGrid(ArrayList<Pokemon> fallenPokemon) {
		int randomRow, randomCol;
		for( int i = 0; i < fallenPokemon.size(); i++ ) {
			do {
				randomRow = (int)(Math.random() * grid.length);
	            randomCol = (int)(Math.random() * grid[0].length);
			}while (grid[randomRow][randomCol] != null);
				grid[randomRow][randomCol] = fallenPokemon.get(i);
		}
	}
	
	public int flip(int xPos, int yPos) {
		if ( (xPos >= 1 && xPos <= 3) && (yPos >= 1 && yPos <= 3) ) {
			if (grid[yPos-1][xPos-1] == null) {                                                    // If the desired flipping block is empty
		        return 0;
		      }else {
		    	  if (grid[yPos-1][xPos-1].getIsFlipped() == false) {  //if the grid is not flipped yet 				
						if (grid[yPos-1][xPos-1] == null) {                         // If the desired flipping block is empty
							//grid[yPos-1][xPos-1].setIsFlipped(true);                                           // Flip it
					        return 0;
					      }else {
					    	  grid[yPos-1][xPos-1].setIsFlipped(true);                                           // Flip it
						        return 1;
					      }
					}else {
						throw new IllegalArgumentException("ERROR!: This location is already flipped!");
					}
		      }
		}else {
			throw new IllegalArgumentException("ERROR!: Input is out of bound!");	
		}
	}
	
	public boolean IsLoginValid(int playerID, String pass) {
		if( playerID == accountPlayerID && pass.equals(accountPass)) {
			return true;
		}
		return false;
	}
	
	public void reset() {
		this.score = 0;
		pInventory.clear();
		pOnField.clear();
		pDead.clear();
	}
	
	public void resetFlip() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid.length; j++) {
				if(grid[i][j] != null) {
					grid[i][j].setIsFlipped(false);
				}
			}
		}
	}
	
	public void resetPoke(Pokemon p) {
		p.setHp(PokeGame.DEFAULT_HP);
	}
	
	//add and set methods==================================================================
	public void setCaughtP(Pokemon p) {
		this.caughtP = p;
	}
	
	public void addPoke( Pokemon poke ) {
		this.pInventory.add(poke);
	}
	
	//overloading
	public void addPoke(ArrayList<Pokemon> poke) {
		for(int i = 0; i < poke.size(); i++) {
			this.pInventory.add(poke.get(i));
		}
	}
	
	public void addOnFieldPoke(Pokemon poke) {
		this.pOnField.add(poke);
	}
	
	public void addDeadPoke(Pokemon poke) {
		this.pDead.add(poke);
	}
	
	public void setPlayerRole(String p) {
		this.playerRole = p;
	}
	
	//get methods =====================================================================================
	public Pokemon getCaughtP() {
		return this.caughtP;
	}
	
	public PokeBall[] getPokeBall() {
		return this.Pokeball;
	}
	
	public Pokemon GetOnFieldP(int index) {
		return this.pOnField.get(index);
	}
	
	public ArrayList<Pokemon> GetInventoryDisk() {
		return this.pInventory;
	}
	
	public ArrayList<Pokemon> GetOnFieldDisk(){
		return this.pOnField;
	}
	
	public ArrayList<Pokemon> GetDeadDisk(){
		return this.pDead;
	}
	
	public String getPlayerRole() {
		return this.playerRole;
	}
	
	//delete methods===================================================================================
	public void deleteOnFieldP( int index ) {
		this.pOnField.remove(index);
	}
	
	public void deleteInventoryP(Pokemon p) {
		 this.pInventory.remove(p);
	}
	
	public void attackVal(int keyVal, Pokemon atkP, Pokemon enemyP, Account enemy) {
		atkP.attackVal(keyVal, enemyP);		
		enemyP.checkStatus();
	}
	
}
