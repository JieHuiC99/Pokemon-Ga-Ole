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
    private static String scoreFile = "Scores.txt";  // filename

    /* INSTANCE VARIABLES */
    private static ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<String> allScores; /* ArrayList that stores the top 5 scores */

    /*=============== CONSTRUCTOR ===============*/
    public Database() {
        allScores = new ArrayList<>();
        importAccounts();
        importScores(); // Import scores from the score file
        // Update the scores file based on account scores
    }

    /*=============== PRIVATE METHODS ===============*/

    private void importAccounts() {
        File file = new File(accountFile);
        try (Scanner sc = new Scanner(file)) {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                while (sc.hasNextLine()) {
                    String infoStr = sc.nextLine().trim();
                    if (!infoStr.isEmpty()) {
                        String[] parts = infoStr.split(",");
                        if (parts.length == 2) { // Only ID and password
                            String playerID = parts[0].trim();
                            String pass = parts[1].trim();
                            Account account = new Account(playerID, pass, true);
                            accounts.add(account);
                        }
                    }
                }
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                // Create a new file if it does not exist
            } else {
                System.out.println("General I/O Exception: " + e.getMessage());
            }
        }
    }

    private void importScores() {
        File file = new File(scoreFile);
        if (!file.exists()) {
            // If the file does not exist, initialize with an empty list
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            allScores.clear(); // Clear existing scores
            while (sc.hasNextLine()) {
                String infoStr = sc.nextLine().trim();
                if (!infoStr.isEmpty()) {
                    String[] parts = infoStr.split(",");
                    if (parts.length == 2) {
                        String playerID = parts[0].trim();
                        int score = Integer.parseInt(parts[1].trim());
                        // Add to allScores only if the score is non-zero
                        if (score > 0) {
                            allScores.add(playerID + "," + score);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("General I/O Exception while importing scores: " + e.getMessage());
        }
    }

    private void updateScoresFromAccounts() {
        ArrayList<String> topScores = getTopScores();
        
        // Only update if top scores have changed
        if (!topScores.equals(allScores)) {
            // Replace allScores with the new top scores
            allScores = new ArrayList<>(topScores);
            rewriteFile(); // Ensure scores file is updated
        }
    }

    private void rewriteFile() {
        ArrayList<String> existingScores = new ArrayList<>();
        File file = new File(scoreFile);

        // Read existing scores from the file into a list
        if (file.exists()) {
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (!line.isEmpty()) {
                        existingScores.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("General I/O Exception while reading existing scores: " + e.getMessage());
            }
        }

        // Open the file in write mode to overwrite it
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(scoreFile, false))) {
            

            // Write all the scores from allScores list
            for (String score : allScores) {
                bWriter.write(score);
                bWriter.newLine();
            }

            // Optionally, write any additional scores not in allScores
            for (String existingScore : existingScores) {
                if (!allScores.contains(existingScore)) {
                    bWriter.write(existingScore);
                    bWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("General I/O Exception while writing scores: " + e.getMessage());
        }
    }
    
    /*=============== PUBLIC METHODS ===============*/
    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account account) {
        accounts.add(account);
        saveAccounts();
    }

    public static boolean saveAccounts() {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(accountFile, false))) {
            for (Account acc : Database.getAccounts()) {
                bWriter.write(acc.getPlayerID() + "," + acc.getPass());
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
        int count = Math.min(5, accounts.size());
        for (int i = 0; i < count; i++) {
            Account acc = accounts.get(i);
            topScores.add(acc.getPlayerID() + "," + acc.getScore());
        }
        return topScores;
    }

    public void displayTopScores() {
        ArrayList<String> topScores = getTopScores();
        for (int i = 0; i < topScores.size(); i++) {
            System.out.println((i + 1) + "." + topScores.get(i));
        }
    }

    public int checkAndSetScores(String playerID, int playerScore) {
        if (playerScore > 0) {
            String scoreEntry = playerID + "," + playerScore;
            allScores.add(scoreEntry);

            // Sorting scores in descending order
            Collections.sort(allScores, (e1, e2) -> {
                try {
                    int score1 = Integer.parseInt(e1.split(",")[1].trim());
                    int score2 = Integer.parseInt(e2.split(",")[1].trim());
                    return Integer.compare(score2, score1); // Sorting in descending order
                } catch (NumberFormatException ex) {
                    // Handle invalid integer cases
                    return 0;
                }
            });

            // Keep only top 5 scores
            if (allScores.size() > 5) {
                allScores = new ArrayList<>(allScores.subList(0, 5));
            }

            // Check if player made it to top 5
            int scoreIndex = allScores.indexOf(scoreEntry);
            if (scoreIndex >= 0) {
                System.out.println("Player made it to top 5, updating file."); // Debug print
                
                rewriteFile();
            } else {
                scoreIndex = playerScore; // if not in top 5, return the player's score
            }

            return scoreIndex;
        } else {
            return playerScore; // if the score is zero, do not add it to allScores
        }
    }

    public ArrayList<String> getAllScores() {
        return this.allScores;
    }

    public void displayAllScores() {
        for (int i = 0; i < allScores.size(); i++) {
            System.out.println((i + 1) + ". " + allScores.get(i));
        }
    }

    public boolean fileExist() {
        File file = new File(scoreFile);
        return file.exists();
    }
}
