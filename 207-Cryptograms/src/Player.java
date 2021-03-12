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
	public String username;
	private int accuracy;
	private int cryptogramsPlayed;
	
	public Player() {
		totalGuesses = 0;
		correctGuesses = 0;
		solved = 0;
		accuracy = 0;
		cryptogramsPlayed = 0;
		username = "Theo";
	}
	
	public int getSolved() {
		return solved;
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
	public void savePlayer() {
		File file = new File("PlayerFile.txt");
		try {
			PrintWriter myWriter = new PrintWriter(file);//this is the file that holds all the player details
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
