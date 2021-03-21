import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Players {
	private ArrayList <Player> allPlayers = new ArrayList<>() ;
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
	                break;
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
		try (Scanner scanner = new Scanner(file)) {
       
			while (scanner.hasNext()) {
				String username = scanner.next().trim();
				double accuracy = scanner.nextDouble();
				int correctGuesses = scanner.nextInt();
				int totalGuesses = scanner.nextInt();
				int cryptogramsPlayed = scanner.nextInt();
				int solved = scanner.nextInt();
				Player player = new Player(totalGuesses,  correctGuesses,solved, accuracy,  cryptogramsPlayed,  username);
				allPlayers.add(player);
			}
			scanner.close();
		} 
		
       
		catch (FileNotFoundException e) {
            System.out.println("Player File can't be found, we can't get any saved players");
            //e.printStackTrace();
        }
		 
	    
		
    }
    
	public void savePlayer(String yesNo, String username) {
		
		File file = new File(playerFile);
		try {
			PrintWriter myWriter = new PrintWriter(file);//this is the file that holds all the player details
			for (int i = 0; i < allPlayers.size(); i++) {
				if(!(yesNo.equalsIgnoreCase("n") && (allPlayers.get(i).getUsername().equals(username)))) {
					myWriter.print(allPlayers.get(i).getUsername() + " ");
					myWriter.print(allPlayers.get(i).getAccuracy() + " ");
					myWriter.print(allPlayers.get(i).getTotalGuesses() + " ");
					myWriter.print(allPlayers.get(i).getCorrectGuesses() + " ");
					myWriter.print(allPlayers.get(i).getSolved() + " ");
					myWriter.print(allPlayers.get(i).getCryptogramsPlayed());
					myWriter.println();
				}
			}
		    myWriter.close();
		    System.out.println("Player details saved");
	    } catch (IOException e) {
	        System.out.println("An error occurred, and details can't be saved");
	        //e.printStackTrace();
	      }
	}

}

