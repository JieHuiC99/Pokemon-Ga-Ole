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
	private ArrayList<String> allScores; /* Arraylist that stores the top 5 scores */
	
	/*=============== CONSTRUCTOR ===============*/
	public Database() {
		allScores =  new ArrayList<String>();
	}
	
	//=========== PUBLIC METHOD ===========//
	   /* Import scores into the database from a textfile.
	    * @return   - The number of scores imported (for debugging purpose incase import wrong number of scores) */ 
	public int ImportScore() {
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
						allScores.add(infoStr);
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
	public int CheckAndSetScores( int playerScore ) {
		String pScore = String.valueOf(playerScore);
		allScores.add(pScore);
		int scoreIndex = -1;
		
		//convert string arraylist to int 
		ArrayList<Integer> Scores = new ArrayList<Integer>();
		for( int i = 0; i < allScores.size(); i++) {
			Scores.add(Integer.parseInt(allScores.get(i)));
		}
		
		Collections.sort(Scores,Collections.reverseOrder());  //sort arraylist in descending order 
		
		//make arraylist size back to 5 
		while(Scores.size() > 5) {
			Scores.remove(Scores.size()-1);
		}
		allScores.clear();//clear arrayist b4 adding back 
		for( int i = 0; i < Scores.size(); i++) {
			allScores.add(String.valueOf(Scores.get(i)));
		}
		
		//check if player make it to top 5
		if(Scores.get(Scores.size()-1) > playerScore) {
			scoreIndex = playerScore;
		}else {
			for( int i = 0; i < Scores.size(); i++) {
				if(Scores.get(i) == playerScore) {
					scoreIndex = i;
				}
			}
		}
		
		//rewrite the file
		try {
			FileWriter myWriter = new FileWriter(fileName);
			BufferedWriter bWriter = new BufferedWriter(myWriter);
			for( int i = 0; i < allScores.size(); i++ ) {
				bWriter.write(allScores.get(i));
				if( i < allScores.size() - 1) {
					bWriter.newLine();
				}				
			}
			bWriter.close();
		}catch(IOException e) {
			System.out.println("General I/O Exception: " + e.getMessage());
		}
		
		return scoreIndex;  
	}
	
	   /* Get the top 5 scores 
	    * @return   - Returns the arraylist that stores 
	    *             all the scores. */
	public ArrayList<String> GetAllScores(){
		return this.allScores;
	}
	
	   /* FOR TESTING PURPOSES */
	   /* Displays the (top 5) scores
	    * on the screen. */
	public void DisplayAllScores() {
		for(int i = 0; i < allScores.size(); i++ ) {
			System.out.println((i+1) + ". " + allScores.get(i));
		}
	}
	
	   /* FOR TESTING PURPOSES */
	   /* Check if file exist
	    * @return   - Returns true if file is found
	    *           - Returns false if file not found  */
	public boolean FileExist() {
		File file = new File("Scores.txt");
		if(file.exists() == true) {
			return true;
		}
		return false;
	}
	
	
	
}
