import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class LettersCryptogram extends Cryptogram{
	private ArrayList<String> phrase;
	private ArrayList<String> encrpytion;
	public LettersCryptogram(String phrase) {
		super(phrase, encrypt(phrase));
		createMap();
	}
	
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
	
	private String convertToString(char input) {
		StringBuilder output = new StringBuilder();
		output.append(input);
		return output.toString();
	}
	public String getLetter(String letter) {
		return letter;
	}
}
