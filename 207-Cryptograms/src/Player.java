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
	private File playerFile = new File("PlayerFile.txt"); //this is here for testing reasons
	
	public Player(int totalGuesses, int correctGuesses,int solved, double accuracy, int cryptogramsPlayed, String username) {
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
	public File getPlayerFile() {
		return playerFile;
	}
	
}
