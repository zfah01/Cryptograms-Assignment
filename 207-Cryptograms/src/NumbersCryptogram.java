import java.util.ArrayList;

public class NumbersCryptogram extends Cryptogram{
	private ArrayList<String> phrase;
	private ArrayList<String> encrpytion;
	public NumbersCryptogram(String inputPhrase) {
		super(inputPhrase, encrypted);
		super.createMap();
	}

}
