import java.util.ArrayList;

public class Cryptogram {
	private String phrase;
	private String encrypted;
	private int frequencies[] = new int[26];
	private static ArrayList<String> Arrayphrase;
	private static ArrayList<String> encrpytion;
	
	public Cryptogram(String inputPhrase,String encrypted) {
		phrase = inputPhrase;
		this.encrypted = encrypted;
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for(int i = 0; i<26; i++) {
			frequencies[i] = calculateFrequency(alphabet.charAt(i), phrase);
		}
		
	}
	
	public String getEncrypted() {
		return encrypted;
	}
	
	private int calculateFrequency(char letter, String Phrase) {
		int count = 0;
		for(int i = 0; i < Phrase.length(); i++) {
			if(Phrase.charAt(i) == letter)
				count++;
		}
		return count;
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	public int[] getFrequency() {
		return frequencies;
	}
	
	
	
	/* 
	 * function that is called on the initialisation of the cryptogram that pairs
	 * all of the letters properly to make finding the unencrypted version easier 
	 * */
	public void createMap() {
		String[] phrasearray = phrase.split("");  //first it splits the string into an array and then converts to arraylist
		String[] encryptarray = encrypted.split("");//because the arraylist is easier to use later on
		for (int i = 0; i > phrase.length(); i++) {
			Arrayphrase.add(phrasearray[i]);
			encrpytion.add(encryptarray[i]);
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
		return Arrayphrase.get(location);
	}
	public static ArrayList<String> getEncryptedArrayList() {
		return encrpytion;
	}
	
	public static ArrayList<String> getPhraseArrayList() {
		return Arrayphrase;
	}

}