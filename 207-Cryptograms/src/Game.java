import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
public class Game {
	private ArrayList<String> phrases;
	private Cryptogram cryptogram; //only used in the decide cryptogram function to avoid an error
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
	
	//helper method to create the cryptogram
	private Cryptogram createLetters() {
		Cryptogram crypto = new LettersCryptogram(phrases.get(ThreadLocalRandom.current().nextInt(1, 14)));
		return crypto;
	}
	
	//helper method to create the cryptogram
	private Cryptogram createNumbers() {
		Cryptogram crypto;
		return crypto;
	}
	
	public Cryptogram decideCryptogram(Scanner scan) {
		boolean decided = false;
		while(!decided) {
			System.out.println("Would you like a numbers or letters cryptogram");
			String type = scan.nextLine();
			if (type.toLowerCase().equals("letters")) {
				cryptogram = createLetters();
			} else if(type.toLowerCase().equals("numbers")) {
				cryptogram = createNumbers();
			} else {
				System.out.println("Sorry that doesn't appear to be an option, would you like numbers or letters?");
			}
		}
		return cryptogram;
	}
	
	public void printEncryption(Cryptogram crypto) {
		String alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z"; //alphabet better cylces through the letters
		int[] frequencies = crypto.getFrequency();
		System.out.println(crypto.getEncrypted());
		System.out.println("Frequencies");
		System.out.println(alphabet);
		for (int i = 0; i < 26; i++) {
			System.out.print(frequencies[i] + " ");
		}
		System.out.println(""); //extra line to break things up
	} 
}
