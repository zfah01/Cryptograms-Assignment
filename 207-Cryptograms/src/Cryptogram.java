import java.util.ArrayList;

public class Cryptogram {
	protected String type;
	protected String phrase;
	protected String encrypted;
	protected int[] frequencies = new int[26];
	protected static ArrayList<String> Arrayphrase;
	protected static ArrayList<String> encrpytion;
	
	public Cryptogram(String inputPhrase,String encrypted) {
		phrase = inputPhrase;
		this.encrypted = encrypted;
		Arrayphrase = new ArrayList<>();
		encrpytion = new ArrayList<>();
		createMap();
		calculateFrequencyArray();
	}
	
	public String getEncrypted() {
		return encrypted;
	}

	protected void calculateFrequencyArray(){
		String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
		for(int i = 0; i<26; i++) {
			frequencies[i] = calculateFrequency(alphabet.charAt(i), encrypted);
		}
	}
	
	protected int calculateFrequency(char letter, String Phrase) {
		int count = 0;
		for(int i = 0; i < Phrase.length(); i++) {
			if(Phrase.charAt(i) == letter)
				count++;
		}
		//System.out.println(count);
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
		char[] phrasearray = phrase.toCharArray();  //first it splits the string into an array and then converts to arraylist
		char[] encryptarray = encrypted.toCharArray();//because the arraylist is easier to use later on

		for (int i = 0; i < phrase.length(); i++) {
				Arrayphrase.add(String.valueOf(phrasearray[i]));
				encrpytion.add(String.valueOf(encryptarray[i]));
		}
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
	public static ArrayList<String> getEncryptedArrayListStatic() {
		return encrpytion;
	}
	
	public ArrayList<String> getEncryptedArrayList() {
		return encrpytion;
	}
	
	public static ArrayList<String> getPhraseArrayListStatic() {
		return Arrayphrase;
	}
	public ArrayList<String> getPhraseArrayList() {
		return Arrayphrase;
	}

	public void printEncryption() {
	}
}
