import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
public class Game {
	private ArrayList<String> phrases;
	public Game() {
		ArrayList<String> phrases = new ArrayList<>();
	}
	
	public void onStartup() throws IOException {
		File file = new File("CryptogramSentences.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (br.readLine() != null) {
			phrases.add(br.readLine());
		}
	}
	
	public Cryptogram createLetters() {
		Cryptogram crypto = new LettersCryptogram(phrases.get(ThreadLocalRandom.current().nextInt(1, 14)));
		return crypto;
	}
	
	public Cryptogram createNumbers() {
		Cryptogram crypto;
		return crypto;
	}
	
	public void printEncryption(Cryptogram crypto) {
		
	} 
}
