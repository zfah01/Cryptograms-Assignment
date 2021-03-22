import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.IOException;
public class Player {
	private int totalGuesses = 0;
	private int correctGuesses = 0;
	private int solved = 0;
	private String username;
	private double accuracy = 0;
	private int cryptogramsPlayed = 0;
	private File playerFile = new File("PlayerFile.txt"); //this is here for testing reasons
	
	public Player(int totalGuesses, int correctGuesses, int solved, double accuracy, int cryptogramsPlayed, String username) {
		this.totalGuesses = totalGuesses;
		this.correctGuesses = correctGuesses;
		this.solved = solved;
		this.accuracy = accuracy;
		this.cryptogramsPlayed = cryptogramsPlayed;
		this.username = username;
	}

	public Player(){}

	public Player(String username){
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
			accuracy = correctGuesses*100/totalGuesses;
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

	public void printPlayerStats() {
		System.out.println("Stats for " + getUsername());
		System.out.println("Accuracy of guesses: " + getAccuracy());
		System.out.println("Total guesses made: " + getTotalGuesses());
		System.out.println("Correct guesses: " + getCorrectGuesses());
		System.out.println("Cryptograms played: " + getCryptogramsPlayed());
		System.out.println("Cryptograms completed: " + getSolved());
	}
	
}
