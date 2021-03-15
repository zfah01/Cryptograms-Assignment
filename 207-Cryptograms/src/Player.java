import java.io.File; 
import java.io.FileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.IOException;
public class Player {
	private int totalGuesses;
	private int correctGuesses;
	private int solved;
	private String username;
	private double accuracy;
	private int cryptogramsPlayed;

	
	public Player(int totalGuesses, int correctGuesses,int solved, double accuracy, int cryptogramsPlayed, String username ) {
		this.totalGuesses = totalGuesses;
		this.correctGuesses = correctGuesses;
		this.solved = solved;
		this.accuracy = accuracy;
		this.cryptogramsPlayed = cryptogramsPlayed;
		this.username = username;
	}
	
	public int getCorrectGuesses() {
		return correctGuesses;
	}
	
	public int getTotalGuesses() {
		return totalGuesses;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public int getCryptogramsPlayed() {
		return cryptogramsPlayed;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getSolved() {
		return solved;
	}
	
	public void updateAccuracy() {
		if(totalGuesses >0)
			accuracy = correctGuesses/totalGuesses;
	}
	
	public void incrementCryptogramsPlayed() {
		cryptogramsPlayed++;
	}
	
	public void addTotalGuesses() {
		totalGuesses ++;
	}
	
	public void addCorrectGuesses() {
		correctGuesses++;
	}
	
	public void addSolved() {
		solved++;
	}
	public void savePlayer(File playerFile) {
		
		try {
			PrintWriter myWriter = new PrintWriter(playerFile);//this is the file that holds all the player details
			myWriter.println(username);
			myWriter.println(totalGuesses);
			myWriter.println(correctGuesses);
			myWriter.println(solved);
			myWriter.println(accuracy);
			myWriter.println(cryptogramsPlayed);
			myWriter.println();
		    myWriter.close();
		    System.out.println("Player details saved");
	    } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
}
