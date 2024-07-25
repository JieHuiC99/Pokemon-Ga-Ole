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
	private static String fileName = "Scores.txt";  //filename
	
	/* INSTANCE VARIABLES */
	private ArrayList<Integer> allScores; /* Arraylist that stores the top 5 scores */
	
	/*=============== CONSTRUCTOR ===============*/
	public Database() {
		allScores =  new ArrayList<>();
	}
	
	/*=============== PRIVATE METHOD ===============*/
	/* Rewrites the file with the current top 5 scores */
	private void rewriteFile() {
		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileName, false))) {
			for (int i = 0; i < allScores.size(); i++) {
				bWriter.write(String.valueOf(allScores.get(i)));
				if (i < allScores.size() - 1) {
					bWriter.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("General I/O Exception: " + e.getMessage());
		}
	}
	
	//=========== PUBLIC METHOD ===========//
	   /* Import scores into the database from a textfile.
	    * @return   - The number of scores imported (for debugging purpose incase import wrong number of scores) */ 
	public int importScore() {
		int scoresImported = 0;
		//String fileName = "Scores.txt";  //filename
		try {
			File file = new File(fileName); 
			Scanner sc = new Scanner(file);
			String infoStr;					
			if(file.exists() == false) { //Check if Scores.txt file exist. if it doesnt, create a new file 
	        	file.createNewFile();
	        }else {
	        	while(sc.hasNextLine()) {
					infoStr = sc.nextLine();
					if( !infoStr.equals("")) { 
						allScores.add(Integer.parseInt(infoStr));
						scoresImported++;
					}
				}
	        }
			sc.close();
		}catch (IOException e) {
			if(e instanceof FileNotFoundException) {
				//create a new file and print nothing
			}else {
				System.out.println("General I/O Exception: " + e.getMessage());
			}
		}
		return scoresImported;
	}
	
	   /* Check if player score made it to top 5
	    * If they do, update the arraylist and txt file 
	    * @param playerScore - Player's score
	    * @return            - Returns the index(ranking is index + 1) of the player's score in the arraylist
	    *                      if they made it into the top 5
	    *                    - Returns player's score if they did not make it into the top 5. */   
	public int checkAndSetScores(int playerScore) {
		allScores.add(playerScore);
		Collections.sort(allScores, Collections.reverseOrder());  //sort arraylist in descending order 
		
		if (allScores.size() > 5) {
			allScores = new ArrayList<>(allScores.subList(0, 5));  // keep only the top 5 scores
		}
		
		// Check if player made it to top 5
		int scoreIndex = allScores.indexOf(playerScore);
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
	public ArrayList<Integer> getAllScores(){
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
