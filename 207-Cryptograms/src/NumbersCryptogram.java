import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NumbersCryptogram extends Cryptogram{
	public NumbersCryptogram(String inputPhrase) {
		super(inputPhrase, encryption(inputPhrase));
		super.createMap();
		type = "numbers";
	}
	
	private static String encryption(String input) {
		int offset = ThreadLocalRandom.current().nextInt(1, 26 + 1);
		StringBuilder t = new StringBuilder();
		for (int i = 0; i < input.length(); ++i) {
		    char ch = input.charAt(i);
		    if(Character.isLetter(ch)) {
				int n = (((int)ch - (int)'A' + offset) % 26 ) + 1;
				t.append(n);
				t.append(",");
		    } else {
				t.append(ch);
		    }

		}
		return t.toString();
	}

	public void printEncryption() {
		int[] frequencies = getFrequency();
		System.out.println(getEncrypted());
		System.out.println("Frequencies");
		for (int i = 0; i < 26; i++) {
			if (i < 9) System.out.print(" ");
			System.out.print( (i + 1) + " ");
		}
		System.out.println();
		for (int i = 0; i < 26; i++) {
			System.out.print(" " + frequencies[i] + " ");
		}
		System.out.println(); //extra line to break things up
	}

	protected void calculateFrequencyArray(){
		String[] encryptedArray = encrypted.split(",");
		for(int i = 0; i<26; i++) {
			frequencies[i] = calculateFrequency(String.valueOf(i+1), encryptedArray);
		}
	}

	protected int calculateFrequency(String iString, String[] encryptedArray) {
		int count = 0;
		for (String s : encryptedArray) {
			if (s.trim().equals(iString))
				count++;
		}
		//System.out.println(count);
		return count;
	}
}
