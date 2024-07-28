import java.awt.FlowLayout;
import java.awt.Image;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Displayer {
	
	Scanner input = new Scanner(System.in);
	private String currentPlayerID;
	
	public Displayer() {
		
	}
	
	public int printLogin(Account player, Account computer) {
	    Pokemon charmander = new FirePokemon("Charmander", "Fire", PokeGame.DEFAULT_HP, PokeGame.DEFAULT_ATK, PokeGame.DEFAULT_DEF);
	    Pokemon pikachu = new ElectricPokemon("Pikachu", "Electric", PokeGame.DEFAULT_HP, PokeGame.DEFAULT_ATK, PokeGame.DEFAULT_DEF);

	    while (true) {
	        try {
	            System.out.println("1. Create Account");
	            System.out.println("2. Login");
	            System.out.println("3. Exit");
	            System.out.print("Option: ");
	            int option = input.nextInt();
	            input.nextLine(); // Consume the newline character

	            switch (option) {
	                case 1: // Create Account
	                    System.out.print("Enter new PlayerID: ");
	                    String newPlayerID = input.nextLine().trim();
	                    if (newPlayerID.isEmpty()) {
	                        System.out.println("ERROR: PlayerID cannot be empty.");
	                        break;
	                    }
	                    System.out.print("Enter new Password: ");
	                    String newPass = input.nextLine().trim();
	                    if (newPass.isEmpty()) {
	                        System.out.println("ERROR: Password cannot be empty.");
	                        break;
	                    }

	                    // Debugging output
	                    System.out.println("Creating account with PlayerID: " + newPlayerID);
	                    
	                    if (Account.createAcc(newPlayerID, newPass)) {
	                        System.out.println("Account created successfully!");
	                    } else {
	                        System.out.println("Failed to create account. PlayerID already exists.");
	                    }
	                    break;

	                case 2: // Login
	                    System.out.print("Enter PlayerID: ");
	                    String playerID = input.nextLine().trim();
	                    if (playerID.isEmpty()) {
	                        System.out.println("ERROR: PlayerID cannot be empty.");
	                        break;
	                    }
	                    System.out.print("Enter Password: ");
	                    String password = input.nextLine().trim();
	                    if (password.isEmpty()) {
	                        System.out.println("ERROR: Password cannot be empty.");
	                        break;
	                    }

	                    if (player.isLoginValid(playerID, password)) {
	                        currentPlayerID = playerID;
	                        player.addPoke(charmander);
	                        player.addPoke(pikachu);
	                        System.out.println("Successfully Logged In!");
	                        System.out.printf("You have %d default Pokemon in your inventory:\n", player.getInventoryDisk().size());
	                        for (Pokemon pokemon : player.getInventoryDisk()) {
	                            printDisk(pokemon);
	                        }
	                        return 1;
	                    } else {  // Login invalid
	                        System.out.println("Your ID or Password is invalid!");
	                        return -1;
	                    }

	                case 3: // Exit
	                    System.out.println("Exiting the game...");
	                    return 0;

	                default:
	                    System.out.println("Invalid option! Please select 1, 2, or 3.");
	            }
	        } catch (Exception e) {
	                if(e instanceof InputMismatchException) {
	                System.out.println("ERROR!: Please input a valid integer for option.");
	                       input.nextLine(); // Clear the invalid input
	                }else {
	                System.out.println("ERROR!: " + e.getMessage());
	                System.out.println("If system doesn't continue key in something and press enter!");
	                       input.nextLine(); // Clear the invalid input
	            
	                }
	        }
	    }
	}
	
	public void printGrid(Pokemon[][] grid) {
		
		System.out.println("===============");
		System.out.println("|   Catch!!   |");
		System.out.println("===============");
		System.out.print("  ");
		for( int i = 1; i < grid[0].length + 1; i++) {
			System.out.print("  " + i + " " );          //column indicator
		}

		System.out.println();
		for(int i = 0; i < grid.length; i++) {      //row
			System.out.println("  -------------");
			System.out.print((i+1) + " ");
			for(int k = 0; k < grid[0].length; k++) {
				if( grid[i][k] == null ) {
					System.out.print("|{?}");
				}else {
					if( grid[i][k].getIsFlipped() == false) {
						System.out.print("|{?}");
					}else {
						System.out.print("| ! ");
					}
				}			
			} //★
			System.out.print("|" + "\n"); 
			
		}
		System.out.println("  -------------");
		System.out.println("===============");
		
	}
	
	public void printDisk(Pokemon p) {
		if(p == null) {
			System.out.println("No Pokemon data available");
			return;
		}
		String currStatus = null;
		p.checkStatus();
		if(p.getStatus() == true) {
			currStatus = "ALIVE";
		}else if(p.getStatus() == false) {
			currStatus = "DEAD";
		}
		System.out.println("--------------------");
		System.out.printf ("|    %-10s    |\n",p.getName());
		System.out.println("--------------------");
		System.out.printf ("|Status  : %-8s|\n", currStatus);
		System.out.println("--------------------");
		System.out.printf ("|HP      : %-8d|\n",p.getHp());
		System.out.printf ("|Attack  : %-8d|\n",p.getBaseAtk());
		System.out.printf ("|Defense : %-8d|\n",p.getBaseDef());
		System.out.printf ("|Element : %-8s|\n",p.getElement());
		System.out.println("--------------------");
		
	}
	
	public ArrayList<Pokemon> printSelectLocation(Account player, Account computer, 
			ArrayList<Pokemon> Ocean, ArrayList<Pokemon> Forest, ArrayList<Pokemon> Volcano){

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
					pReturn = Ocean;
					pokemonGUI("Ocean", Ocean);
					locationLoop = false;
					break;
					
				case 2:
					System.out.println("Volcano - Ninetales, Vulpix, Ponyta");
					pReturn = Volcano;
					pokemonGUI("Volcano", Volcano);
					locationLoop = false;
					break;
					
				case 3:
					System.out.println("Forest - Jolteon, Voltorb, Magnemite");
					pReturn = Forest;
					pokemonGUI("Forest", Forest);
					locationLoop = false;
					break;
				default:
					System.out.println("ERROR!: Choose 1-3!");
					break;
				}
			}catch(Exception e) {
				System.out.println("ERROR!: " + e.getMessage());
				input.next();
			}
			
		}
		return pReturn;

	}

	
	public void printCatch(Account player, Account computer, ArrayList<Pokemon> fallenPokemon) {
		player.setCaughtP(null);
		player.placePokeInGrid(fallenPokemon);  //place pokemon in grid to catch 
		//boolean running = true;
		int chance =(int) (Math.random() * 3);
		
		System.out.print("Randomizing a Poke Ball... (Press enter to continue if game doesnt continue)...");		
		input.nextLine();
		//input.next();
		System.out.println("You got...");
		System.out.println(player.getPokeball()[chance].toString());
		
		int i = 0;
		while(i < player.getPokeball()[chance].getChance()) {
			try {
				printGrid(player.getGrid());
				System.out.print("x-Cord to move/flip: ");                        
		        int xPos = input.nextInt() - 1;
		        System.out.print("y-Cord to move/flip: ");
		        int yPos = input.nextInt() - 1;
		        int flip = player.flip(xPos, yPos);
		        if( flip == 0) {
		        	System.out.println("Oops, nothing is there! :(");
		        	i++;
		        }else if(flip == 1) {
		        	printGrid(player.getGrid());
		        	System.out.println("Congratulations! You have caught a pokemon!");
		        	Pokemon caught = player.getGrid()[yPos][xPos];
		        	player.addPoke(caught);
		        	player.setCaughtP(caught);
		        	break;
		        }
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("NOTE: If game doesn't continue, key in anything and press enter!");
				input.next();
				
			}
		}	
	}
	
	public void printSelectPoke(Account player, Account computer) {
		
		System.out.println("Here are your pokemons!");
		
		for( int i = 0; i < player.getInventoryDisk().size(); i++) {
			System.out.println("Pokemon " + (i+1) + ":");
			printDisk(player.getInventoryDisk().get(i));
		}
		System.out.println("Choose your pokemon to be on battle!");
		int i = 0, j = 0;
		while(i < 2) {
			boolean same = false;
			try {
				System.out.print("Pokemon " + (i+1) + ": ");
				int pokeOption = input.nextInt();
				for(int x = 0; x < player.getOnFieldDisk().size(); x++) {
					if(player.getOnFieldDisk().size() != 0) {
						if( player.getInventoryDisk().get(pokeOption-1) == player.getOnFieldDisk().get(x)) {
							System.out.println("ERROR!: Pokemon is already in inventory!");
							same = true;
						}
					}
				}
				if(!same) {
					player.addOnFieldPoke(player.getInventoryDisk().get(pokeOption-1));
					for( int k = 0; k < computer.getInventoryDisk().size(); k++) {
						if(player.getInventoryDisk().get(pokeOption-1) == computer.getInventoryDisk().get(k)) {
							computer.deleteInventoryP(computer.getInventoryDisk().get(k));
						}
					}
					i++;
				}
				
			}catch(Exception e) {
				if(e instanceof InputMismatchException) {
					System.out.println("ERROR!: Please input integer for option and ID and String for pass!");
					input.next();
				}else {
					System.out.println("ERROR!: Please enter an integer within the range.");
				}
			}
		}
		
		while(j < 2) {
			boolean same = false;
			int randPoke =(int) (Math.random() * computer.getInventoryDisk().size());
			for(int y = 0; y < computer.getOnFieldDisk().size(); y++) {
				if(computer.getOnFieldDisk().size() != 0) {
					if( computer.getInventoryDisk().get(randPoke) == computer.getOnFieldDisk().get(y)) {
						same = true;
					}
				}
			}
			if(!same) {
				computer.addOnFieldPoke(computer.getInventoryDisk().get(randPoke));
				j++;
			}
		}
	}
	
	public void printManageScores(Account[] players, int score, Database db) {
	    // Update the scores for the current player
	    int rank = db.checkAndSetScores(currentPlayerID, score);
	    
	    // Save updated scores without losing other account details
	    if (!db.saveAccounts()) {
	        System.out.println("Error saving accounts!");
	    }
	
	    // Retrieve and display all scores
	    ArrayList<String> allScores = db.getAllScores();
	    
	    // Print player scores
	    for (Account player : players) {
	        String role = player.getPlayerRole().equals("PLAYER") ? "Player" : "Enemy";
	        System.out.println(role + "'s score: " + player.getScore());
	    }
	    
	    // Print top 5 scores
	    System.out.println("=====================");
	    System.out.println("|    TOP 5 SCORES   |");
	    System.out.println("=====================");
	    for (int i = 0; i < Math.min(allScores.size(), 5); i++) {
	        String[] parts = allScores.get(i).split(",");
	        String playerID = parts[0];
	        int playerScore = Integer.parseInt(parts[1]);
	        System.out.printf("| %d. %-10s %d|\n", i + 1, playerID, playerScore);
	    }
	    
	    System.out.println("---------------------");
	    if (rank == score) {
	        System.out.println("You did not make it into the top 5, better luck next time!");
	    } else {
	        if (rank <= 4 && rank >= 0) {
	            System.out.println("Congratulations! You have made it into the top " + (rank + 1) + "!");
	        }
	    }
	}

	
	// GUI for Pokemon
		
		public void pokemonGUI(String title, ArrayList<Pokemon> pokemons)	{
			JFrame frame = new JFrame("Pokemon - " + title);
	        frame.setSize(650, 275);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setResizable(false);
	        frame.setLocationRelativeTo(null);
	        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

	        for (Pokemon pokemon : pokemons) {
	            ImageIcon image = new ImageIcon(new ImageIcon(Displayer.class.getResource(pokemon.getName() + ".png")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
	            JLabel label = new JLabel(image);
	            label.setText(pokemon.getName());
	            setTextPosition(label, JLabel.CENTER, JLabel.BOTTOM);
	            frame.add(label);
	        }
	        
	        frame.setVisible(true);
	        frame.setAlwaysOnTop(true);
		}
		
		private void setTextPosition(JLabel label, int horizontalAlignment, int verticalAlignment) {
		    label.setHorizontalTextPosition(horizontalAlignment);
		    label.setVerticalTextPosition(verticalAlignment);
		}
	
	
}
