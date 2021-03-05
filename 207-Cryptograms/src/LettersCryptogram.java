import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class LettersCryptogram extends Cryptogram{
	public LettersCryptogram(String phrase) {
		super(phrase, encrypt(phrase));
		super.createMap();
	}
	
	/* 
	 * function that encrypts the phrase thats passed through using a ceaser cipher for
	 * the user to see before solving it
	 * */
	private static String encrypt(String phrase) {
		int offset = ThreadLocalRandom.current().nextInt(1, 26 + 1);
		StringBuilder result = new StringBuilder();
		for (char character : phrase.toCharArray()) {
		    if (character != ' ') {
		        int originalAlphabetPosition = character - 'a';
		        int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
		        char newCharacter = (char) ('a' + newAlphabetPosition);
		        result.append(newCharacter);
		    } else {
		        result.append(character);
		    }
		}
		return result.toString(); 	
	}
	
}
