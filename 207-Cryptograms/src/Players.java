import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Players {
	private ArrayList <Player> allPlayers;
	private  String playerFile = "playerFile.txt";
	
	public void addPlayer(Player p) {
		
		allPlayers.add(p);
	}
	

	
	public Player findPlayer(String username) {
		Player seekedPlayer = null;
		 for(Player player : allPlayers)
	        {
	            if(player.getUsername().equals(username)) {
	                seekedPlayer = player;
	            }
	        }
		return seekedPlayer;
		
	}
	
	
	public ArrayList<Double> getAllPlayersAccuracies() {
		ArrayList<Double> allPlayersAccuracies = new ArrayList<Double>();
		
		for(Player player :allPlayers) {
			allPlayersAccuracies.add(player.getAccuracy());
		}
		
		return allPlayersAccuracies ;
	}
	
	public ArrayList<Integer> getAllPlayersCryptogramsPlayed() {
   ArrayList<Integer> allPlayersCryptogramsPlayed = new ArrayList<Integer>();
		
		for(Player player :allPlayers) {
			allPlayersCryptogramsPlayed.add(player.getCryptogramsPlayed());
		}
		
		return allPlayersCryptogramsPlayed;
	}
	
	public ArrayList<Integer> getAllPlayersCompletedCryptos() {
		ArrayList<Integer> allPlayersCompletedCryptos = new ArrayList<Integer>();
		
		for(Player player : allPlayers) {
			allPlayersCompletedCryptos.add(player.getSolved());
		}
			
		return allPlayersCompletedCryptos;
			
		}
	
    public void loadPlayers() {
    	
		File file = new File(playerFile);
		Player p = new Player(null, 0, 0, 0, 0, 0);
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String username = scanner.next();
				double accuracy = scanner.nextDouble();
				int correctGuesses = scanner.nextInt();
				int totalGuesses = scanner.nextInt();
				int cryptogramsPlayed = scanner.nextInt();
				int solved = scanner.nextInt();
				Player player = new Player(username, accuracy, correctGuesses,totalGuesses, cryptogramsPlayed,solved );
				allPlayers.add(player);
			}
			scanner.close();
		} 
    
		catch (FileNotFoundException e) {
            System.out.println("File Not Found: This player does not exist");
            
            allPlayers.add(p);
		    System.out.println("New Player has been created : "+ p.getUsername());
        }
		
		
	    catch (IOException e) {
			System.out.println("Error: Something went wrong while loading player details");
		}
		
    }
}

