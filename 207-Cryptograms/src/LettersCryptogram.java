import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class LettersCryptogram extends Cryptogram{
	private ArrayList<String> phrase;
	private ArrayList<String> encrpytion;
	public LettersCryptogram(String phrase) {
		super(phrase, encrypt(phrase));
		createMap();
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
	
	/* 
	 * function that is called on the initialisation of the cryptogram that pairs
	 * all of the letters properly to make finding the unencrypted version easier 
	 * */
	public void createMap() {
		String StringPhrase = super.getPhrase();
		String StringEncrypted = super.getEncrypted();
		ArrayList<String> letters = new ArrayList<String>();
		for(int i = 0; i < 26; i++) {
			if(!letters.contains(convertToString(StringPhrase.charAt(i)))) {
				phrase.add(convertToString(StringPhrase.charAt(i)));
				encrpytion.add(convertToString(StringEncrypted.charAt(i)));
			}
		}
	}
	
	/* 
	 * function that inputs a char and converts it to a string 
	 * because the arraylist doesn't accept chars
	 * */
	private String convertToString(char input) {
		StringBuilder output = new StringBuilder();
		output.append(input);
		return output.toString();
	}
	
	
	/* 
	 * the letter version of these that passes in a letter and gets the
	 * unencrypted version of it, returns empty if letter doesn't exist
	 * */
	public String getLetter(String letter) {
		int location = encrpytion.indexOf(letter);
		if(location == -1) {
			return "";
		}
		return phrase.get(location);
	}
}
