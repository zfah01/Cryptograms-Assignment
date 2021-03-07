import java.util.ArrayList;

public class NumbersCryptogram extends Cryptogram{
	public NumbersCryptogram(String inputPhrase) {
		super(inputPhrase, encryption(inputPhrase));
		super.createMap();
	}
	
	private static String encryption(String input) {
		String t = "";
		for (int i = 0; i < input.length(); ++i) {
		    char ch = input.charAt(i);
		    if(ch == ' ') {
		    	t += " ";
		    } else { 
		    	int n = (int)ch + (int)'a' + 1;
		    	t += String.valueOf(n);
		    	t += ",";
		    }
		}
		return t;
	}
}
