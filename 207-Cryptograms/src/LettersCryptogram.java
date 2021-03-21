import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class LettersCryptogram extends Cryptogram{
	public LettersCryptogram(String phrase) {
		super(phrase, encrypt(phrase));
		super.createMap();
		type = "letters";
	}
	
	/* 
	 * function that encrypts the phrase thats passed through using a ceaser cipher for
	 * the user to see before solving it
	 * */
	private static String encrypt(String phrase) {
		int offset = ThreadLocalRandom.current().nextInt(1, 26 + 1);
		StringBuilder result = new StringBuilder();
		for (char character : phrase.toCharArray()) {
		    if (Character.isLetter(character)) {
		        int originalAlphabetPosition = character - 'A';
		        int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
		        char newCharacter = (char) ('A' + newAlphabetPosition);
		        result.append(newCharacter);
		    } else {
		        result.append(character);
		    }
		}
		return result.toString(); 	
	}

	public void printEncryption() {
		String alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z".toUpperCase(); //alphabet better cycles through the letters
		int[] frequencies = getFrequency();
		System.out.println(getEncrypted());
		System.out.println("Frequencies");
		System.out.println(alphabet);
		for (int i = 0; i < 26; i++) {
			System.out.print(frequencies[i] + " ");
		}
		System.out.println(); //extra line to break things up
	}
	
}
