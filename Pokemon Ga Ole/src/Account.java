import java.util.ArrayList;
import java.io.Serializable;

public class Account implements Serializable {
    
    private static final long serialVersionUID = 1L; 
    // Login details
    private String playerID;
    private String pass;
    // Role of the player (PLAYER or ENEMY)
    private String playerRole;
    // Player's items 
    private ArrayList<Pokemon> pInventory;
    private ArrayList<Pokemon> pOnField;
    private ArrayList<Pokemon> pDead;
    private Pokemon caughtP;
    private int score;
    private PokeBall[] Pokeball;
    private Pokemon[][] grid;
    
  public Account(String playerID, String pass, boolean isUser) {
	this.playerID = playerID;
    this.pass = pass;
    this.playerRole = isUser ? "PLAYER" : "ENEMY";
    pInventory = new ArrayList<>();
    pOnField = new ArrayList<>();
    pDead = new ArrayList<>();
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
    
    public void setGrid(Pokemon[][] grid) {
		this.grid = grid;
	}

    public int getScore() {
        return score;
    }
	
    public void setScore(int score) {
        this.score = score;
    }
    
    public Pokemon getCaughtP() {
        return this.caughtP;
    }
    
    public void setCaughtP(Pokemon p) {
        this.caughtP = p;
    }

    public String getPlayerRole() {
        return this.playerRole;
    }   
    
    public void setPlayerRole(String p) {
        this.playerRole = p;
    }
   
   public static boolean createAcc(String playerID, String pass) {
    if (playerID == null || playerID.trim().isEmpty()) {
        System.out.println("ERROR: PlayerID cannot be empty.");
        return false;
    }
    
    if(playerID.length() > 10) {
    	throw new IllegalArgumentException("PlayerID length cannot exit 10 characters!");
    }
    
    // Check if account already exists
    for (Account acc : Database.getAccounts()) {
        if (acc.getPlayerID().equals(playerID)) {
            System.out.println("ERROR: Account with PlayerID " + playerID + " already exists.");
            return false; // Account already exists
        }
    }

    // Create new account
    Account newAccount = new Account(playerID, pass, true);
    Database.getAccounts().add(newAccount);

    // Debug print
    System.out.println("Created account with ID: " + newAccount.getPlayerID());

    // Save to file
    return Database.saveAccounts();
}
    
    public String getPlayerID() {
        return playerID;
    }
    
    public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getPass() {
        return pass;
    }
    
    public void setPass(String pass) {
		this.pass = pass;
	}

	/* check if login is valid 
     * @param playerID - player's id
     * @param pass     - password
     * @return         - true if login is valid
     *                 - false if login is invalid  */   
    public boolean isLoginValid(String playerID, String pass) {
        for (Account acc : Database.getAccounts()) {
            if (acc.getPlayerID().equals(playerID) && acc.getPass().equals(pass)) {
                return true;
            }
        }
        return false;
    }
    
    /* clear player's inventory and reset the score to 0  */   
    public void reset() {
        this.score = 0;
        pInventory.clear();
        pOnField.clear();
        pDead.clear();
    }
    
    /* place an arraylist of (fallen)pokemons into the 2D array
     * randomize their positions in the 2D array
     * @param fallenPokemon - the fallen pokemons to placed in grid and caught by players  */   
    public void placePokeInGrid(ArrayList<Pokemon> fallenPokemon) {
        int randomRow, randomCol;
        for (int i = 0; i < fallenPokemon.size(); i++) {
            do {
                randomRow = (int)(Math.random() * grid.length);
                randomCol = (int)(Math.random() * grid[0].length);
            } while (grid[randomRow][randomCol] != null);
            grid[randomRow][randomCol] = fallenPokemon.get(i);
        }
    }

    /* flip a grid to see if there is a pokemon inside the grid 
     * @param xPos - x position (horizontal)
     * @param yPos - y position (vertical)
     * @return     - 0 if null
     *             - 1 if pokemon is found */   
    public int flip(int xPos, int yPos) {
        if (xPos >= 0 && xPos < grid[0].length && yPos >= 0 && yPos < grid.length) {
            if (grid[yPos][xPos] == null) { // If the desired flipping block is empty
                return 0;
            } else {
                if (!grid[yPos][xPos].getIsFlipped()) { // If the grid is not flipped yet
                    grid[yPos][xPos].setIsFlipped(true); // Flip it
                    return 1;
                } else {
                    throw new IllegalArgumentException("ERROR!: This location is already flipped!");
                }
            }
        } else {
            throw new IllegalArgumentException("ERROR!: Input is out of bounds!");    
        }
    }
    
    /* reset the grid by making it empty  */   
    public void resetFlip() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    grid[i][j].setIsFlipped(false);
                    grid[i][j] = null;
                }
            }
        }
    }
    
    /* reset pokemon hp back to default value
     * @param p  - pokemon object  */   
    public void resetPoke(Pokemon p) {
        p.setHp(PokeGame.DEFAULT_HP);
    }
    
    public void addPoke(Pokemon poke) {
        this.pInventory.add(poke);
    }
    
    // Overloading
    public void addPoke(ArrayList<Pokemon> poke) {
        this.pInventory.addAll(poke);
    }
    
    public void addOnFieldPoke(Pokemon poke) {
        this.pOnField.add(poke);
    }
    
    public void addDeadPoke(Pokemon poke) {
        this.pDead.add(poke);
    }
    
    public PokeBall[] getPokeball() {
        return this.Pokeball;
    }

	public void setPokeball(PokeBall[] Pokeball) {
		this.Pokeball = Pokeball;
	}

	public Pokemon getOnFieldP(int index) {
        return this.pOnField.get(index);
    }
  
	public ArrayList<Pokemon> getInventoryDisk() {
        return this.pInventory;
    }
    
    public ArrayList<Pokemon> getOnFieldDisk() {
        return this.pOnField;
    }
    
    public ArrayList<Pokemon> getDeadDisk() {
        return this.pDead;
    }
    
    public void deleteOnFieldP(int index) {
        this.pOnField.remove(index);
    }
    
    public void deleteInventoryP(Pokemon p) {
        this.pInventory.remove(p);
    }
    
    /* attack the pokemon
     * check enemy pokemon status see if they are alive or dead
     * if dead, remove them from on field  
     * @param keyVal - the amount of times player spammed the key (GUI)
     * @param atkP   - currentPlayer's attacker pokemon
     * @param enemyP - enemy Pokemon object
     * @param enemy  - enemy player  */   
    public void attackVal(int keyVal, Pokemon atkP, Pokemon enemyP, Account enemy) {
        atkP.attackVal(keyVal, enemyP);        
        enemyP.checkStatus();
    }
}
