import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Database {
	
	/* STATIC VARIABLES */
	private static String accountFile = "Accounts.txt";
//	private static String scoreFile = "Scores.txt";  //filename
	
	/* INSTANCE VARIABLES */
	private static ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<String> allScores; /* Arraylist that stores the top 5 scores */
	
	/*=============== CONSTRUCTOR ===============*/
	public Database() {
		allScores =  new ArrayList<>();
		importAccounts();
		updateScoresFromAccounts();
//		importScores();
		
	}
	
	/*=============== PRIVATE METHOD ===============*/
	/* Rewrites the file with the current top 5 scores */
	private void rewriteFile() {
		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(accountFile, false))) {
			for (String score : allScores) {
				bWriter.write(score);
				bWriter.newLine();
				}
		} catch (IOException e) {
			System.out.println("General I/O Exception: " + e.getMessage());
		}
	}
	
	
	
	
	
	
	private void importAccounts() {
		File file = new File(accountFile);
        try (Scanner sc = new Scanner(file)) {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                while (sc.hasNextLine()) {
                    String infoStr = sc.nextLine();
                    if (!infoStr.trim().isEmpty()) {
                        String[] parts = infoStr.split(",");
                        if (parts.length == 3) {
                            String playerID = parts[0].trim();
                            String pass = parts[1].trim();
                            int score = Integer.parseInt(parts[2].trim());
                            Account account = new Account(playerID, pass, true);
                            account.setScore(score);
                            accounts.add(account);
                        }
                    }
                }
            }
	        sc.close();
	    } catch (IOException e) {
	        if (e instanceof FileNotFoundException) {
	            // Create a new file and print nothing
	        } else {
	            System.out.println("General I/O Exception: " + e.getMessage());
	        }
	    }
	}

	private void updateScoresFromAccounts() {
		allScores.clear();
		ArrayList<String> topScores = getTopScores();
		for (String score : topScores) {
			allScores.add(score);
		}
	}
	
	
	public static ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	public static void addAccount(Account account) {
		accounts.add(account);
		saveAccounts();
	}
	
	public static boolean saveAccounts() {
		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(accountFile, false))) {
            for (Account acc : accounts) {
                bWriter.write(acc.getPlayerID() + "," + acc.getPass() + "," + acc.getScore());
                bWriter.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("General I/O Exception: " + e.getMessage());
            return false;
        }
	}
	
	
	public ArrayList<String> getTopScores() {
		ArrayList<String> topScores = new ArrayList<>();
		Collections.sort(accounts, (a1, a2) -> Integer.compare(a2.getScore(), a1.getScore()));
		int count = Math.min(5,  accounts.size());
		for(int i = 0; i < count; i++) {
			Account acc = accounts.get(i);
			topScores.add(acc.getPlayerID() + "," + acc.getScore());
		}
		return topScores;
	}
	
	public void displayTopScores() {
		ArrayList<String> topScores = getTopScores();
		for(int i = 0; i < topScores.size(); i++) {
			System.out.println((i+1) + "." + topScores.get(i));
		}
	}
	
	
	
	
	//=========== PUBLIC METHOD ===========//
	   /* Import scores into the database from a textfile.
	    * @return   - The number of scores imported (for debugging purpose incase import wrong number of scores) */ 
//	public int importScores() {
//		int scoresImported = 0;
//		//String fileName = "Scores.txt";  //filename
//		try {
//			File file = new File(accountFile); 
//			Scanner sc = new Scanner(file);
//			String infoStr;
//			if(file.exists() == false) { //Check if Scores.txt file exist. if it doesnt, create a new file 
//	        	file.createNewFile();
//	        }else {
//	        	while(sc.hasNextLine()) {
//					infoStr = sc.nextLine();
//					if( !infoStr.equals("")) { 
//						allScores.add(infoStr.trim());
//							scoresImported++;
//						}
//					}
//				}
//			sc.close();
//		}catch (IOException e) {
//			if(e instanceof FileNotFoundException) {
//				//create a new file and print nothing
//			}else {
//				System.out.println("General I/O Exception: " + e.getMessage());
//			}
//		}
//		return scoresImported;
//	}
	
	   /* Check if player score made it to top 5
	    * If they do, update the arraylist and txt file 
	    * @param playerScore - Player's score
	    * @return            - Returns the index(ranking is index + 1) of the player's score in the arraylist
	    *                      if they made it into the top 5
	    *                    - Returns player's score if they did not make it into the top 5. */   
	public int checkAndSetScores(String playerID, int playerScore) {
		String scoreEntry = playerID + "," + playerScore;
		allScores.add(scoreEntry);
		Collections.sort(allScores, (e1, e2) -> {
			try {
				int score1 = Integer.parseInt(e1.split(",")[1].trim());
				int score2 = Integer.parseInt(e2.split(",")[1].trim());
				return Integer.compare(score2, score1); // Sorting in descending order
			} catch (NumberFormatException ex) {
				// Handle the case where the score is not a valid integer
				return 0;
			}
		});
		if (allScores.size() > 5) {
			allScores = new ArrayList<>(allScores.subList(0, 5));  // keep only the top 5 scores
		}
		
		// Check if player made it to top 5
		int scoreIndex = allScores.indexOf(scoreEntry);
		if (scoreIndex >= 0) {
			rewriteFile();
		} else {
			scoreIndex = playerScore; // if not in top 5, return the player's score
		}
		
		return scoreIndex;
	}
		
	   /* Get the top 5 scores 
	    * @return   - Returns the arraylist that stores 
	    *             all the scores. */
	public ArrayList<String> getAllScores(){
		return this.allScores;
	}
	
	   /* FOR TESTING PURPOSES */
	   /* Displays the (top 5) scores
	    * on the screen. */
	public void displayAllScores() {
		for(int i = 0; i < allScores.size(); i++ ) {
			System.out.println((i+1) + ". " + allScores.get(i));
		}
	}
	
	   /* FOR TESTING PURPOSES */
	   /* Check if file exist
	    * @return   - Returns true if file is found
	    *           - Returns false if file not found  */
	public boolean fileExist() {
		File file = new File("Scores.txt");
		if(file.exists() == true) {
			return true;
		}
		return false;
	}
	
	
}
