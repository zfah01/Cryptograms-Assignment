import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
public class Game {
	public ArrayList<String> guesses;//array with letters guessed
	public ArrayList<String> crypt; //= Cryptogram.getPhrase//array with cryptogram and guesses
	public ArrayList<String> values = new ArrayList<String>();//holds the values the guess replaces onlywhen replaced
	public ArrayList<Integer> valuePlaces = new ArrayList<Integer>();//keeps track of where the values were before being replaced
	public ArrayList<String> answer;//will be changed to hold answer
	private Cryptogram cryptogram; //only used in the decide cryptogram function to avoid an error
	private ArrayList<String> phrases;
	public Game() {
		ArrayList<String> phrases = new ArrayList<>();
	}
	
	public void onStartup() throws IOException {
		File file = new File("CryptogramSentences.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (br.readLine() != null) {
			phrases.add(br.readLine());
		}
	}
	
	//helper method to create the cryptogram
	private Cryptogram createLetters() {
		Cryptogram crypto = new LettersCryptogram(phrases.get(ThreadLocalRandom.current().nextInt(1, 14)));
		return crypto;
	}
	
	//helper method to create the cryptogram
	private Cryptogram createNumbers() {
		Cryptogram crypto = new NumbersCryptogram(phrases.get(ThreadLocalRandom.current().nextInt(1, 14)));
		return crypto;
	}
	
	public Cryptogram decideCryptogram(Scanner scan) {
		boolean decided = false;
		while(!decided) {
			System.out.println("Would you like a numbers or letters cryptogram");
			String type = scan.nextLine();
			if (type.toLowerCase().equals("letters")) {
				cryptogram = createLetters();
			} else if(type.toLowerCase().equals("numbers")) {
				cryptogram = createNumbers();
			} else {
				System.out.println("Sorry that doesn't appear to be an option, would you like numbers or letters?");
			}
		}
		return cryptogram;
	}
	
	public void printEncryption(Cryptogram crypto) {
		String alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z"; //alphabet better cylces through the letters
		int[] frequencies = crypto.getFrequency();
		System.out.println(crypto.getEncrypted());
		System.out.println("Frequencies");
		System.out.println(alphabet);
		for (int i = 0; i < 26; i++) {
			System.out.print(frequencies[i] + " ");
		}
		System.out.println(""); //extra line to break things up
	}
	
	public void enterLetter(Cryptogram crypto, Player player) {
		crypt.add(crypto.getEncrypted());
		boolean guessed = false;//checks if guess has been made or value has been replaced
		boolean correct = false;//checks if whole cryptogram is correct
		int mapped = crypt.size();
		Scanner myObj = new Scanner(System.in);
		System.out.println("Please enter your guess: ");
		String guess = myObj.nextLine();//this is the guess
		for(int i = 0; i < guesses.size(); i++) {//checks if letter has been guessed before
			if(guesses.get(i).equals(guess))//change to different array
				guessed = true;
		}
		if(guessed == true) {
			System.out.println("You have already guessed this letter. Please try again.");//error message if letter has been guessed
		}else {
			guesses.add(guess);//guess has been added to array of guesses
			System.out.println("Please enter the value to replace your guess with: ");
			String value = myObj.nextLine();// get value to replace with guess
			//find value in cryptogram array crypt
			boolean valueThere = false;
			int replaceAt = 0;
			int guessedAt = 0;
			for(int i = 0; i < values.size(); i++) {//checks if value has been guessed before
				if(values.get(i).equals(value)) {
					guessed = true;
					guessedAt = valuePlaces.get(i);//gets the position of where the value used to be in the crypt
				}else
					guessed = false;
				
			}
			if(guessed == true) {//what happens if value has been guessed before
				System.out.println("You have already replaced this value. Do you want to overwrite this? y/n");
				String yesno = myObj.nextLine();
				if(yesno.equals("n"))//if you want to cancel your guess
					return;//kill the method
			}
			//checks if value is in cryptogram
			for(int i = 0; i < crypt.size();i++) {
				if(crypt.get(i).equals(value)) {
					valueThere = true;
					replaceAt = i;//need a value where the value definitely has been
				}
			}
			if((valueThere == false)&&(guessed == false)) {
				System.out.println("ERROR: value selected not in cryptogram");
				return;
			}
			if(guessed ==false)	{//what happens if this is the first time the value is being mapped
				values.add(value);
				valuePlaces.add(replaceAt);//need to remember where the previous value was
				for(int i = 0; i<crypt.size();i++) {
					if(crypt.get(i).equals(value))
						crypt.set(i, guess);
				}
				guesses.add(guess);
				mapped--;
				//check if guess was correct?
				if(crypto.getLetter(value).equals(guess)) {
					player.addCorrectGuesses();
				}
				player.addTotalGuesses();
			}else {//what happens if this not the first time the value is being mapped
				for(int i = 0; i<crypt.size();i++) {
					if(crypt.get(i).equals(crypt.get(valuePlaces.get(guessedAt))))//this compares what is in crypt to 
						crypt.set(i, guess);
				}
				guesses.add(guess);//add guess
				if(crypto.getLetter(value).equals(guess)) {
					player.addCorrectGuesses();
				}
				player.addTotalGuesses();
			}
			if(mapped == 0) {
				for(int i = 0; i <answer.size();i++) {
					if(crypt.get(i).equals(answer.get(i))==false) {
						System.out.println("User has failed cryptogram :(");
					}else {
						System.out.println("User has successfully completed cryptogram!!");
						player.addSolved();
					}
						
				}
			}

		}
		myObj.close();
	}
}
