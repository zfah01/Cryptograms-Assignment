
public class Cryptogram {
	private String phrase;
	private String encrypted;
	private int frequencies[] = new int[26];
	
	public Cryptogram(String inputPhrase, String encrypted) {
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
	
	public int[] getFrequency() {
		return frequencies;
	}
	
	
}
