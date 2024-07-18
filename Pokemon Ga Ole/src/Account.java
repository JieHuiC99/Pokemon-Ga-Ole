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
	private ArrayList<Pokemon> onField;
	private int score;
	PokeBall[] Pokeball;
	
	Pokemon[][] grid;
	
//	public Account(int playerID, String pass) {
//		this.playerID = playerID;
//		this.pass = pass;
//	}
	
	public Account(int num) {
		
		if(num == 0) {
			this.playerRole = "PLAYER";
		}else if( num == 1) {
			this.playerRole = "COMPUTER";
		}
		
		pInventory = new ArrayList<Pokemon>();
		onField = new ArrayList<Pokemon>();
		grid = new Pokemon[3][3];
		Pokeball = new PokeBall[3];
		Pokeball[0] = new PokePoke(2, "Poke Ball");
		Pokeball[1] = new PokeGreat(4, "Great Ball");
		Pokeball[2] = new PokeUltra(6, "Ultra Ball");
		
		
		
//		Pokemon p1 = new Pikachu(10000,10000,10000,4);
//		Pokemon p2 = new Charmander(10000,10000,10000,4);
//		p.add(p1);
//		p.add(p2);
	}
	
	public Pokemon[][] getGrid() {
		return this.grid;
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
	
	public void addPoke( Pokemon poke ) {
		this.pInventory.add(poke);
	}
	
	//overloading
	public void addPoke(Pokemon[][] poke) {
		
	}
	
	//overloading
	public void addPoke(ArrayList<Pokemon> poke) {
		
	}
	
	public PokeBall[] getPokeBall() {
		return this.Pokeball;
	}
	
	public boolean IsLoginValid(int playerID, String pass) {
		if( playerID == accountPlayerID && pass.equals(accountPass)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Pokemon> GetDisk() {
		return this.pInventory;
	}
	
}
