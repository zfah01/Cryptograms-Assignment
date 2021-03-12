import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
public class Game {
	public ArrayList<String> guesses = new ArrayList<String>();//array with letters guessed
	public ArrayList<String> crypt;//array with cryptogram and guesses
	public ArrayList<String> crypt2;//hold unchanged encrypted cryptogram
	public ArrayList<String> values = new ArrayList<String>();//holds the values the guess replaces onlywhen replaced
	public ArrayList<Integer> valuePlaces = new ArrayList<Integer>();//keeps track of where the values were before being replaced
	public ArrayList<String> answer;//will be changed to hold answer
	private Cryptogram cryptogram; //only used in the decide cryptogram function to avoid an error
	private ArrayList<String> phrases = new ArrayList<>();
	public boolean checkPrint = false;//used for testing purposes
	public int mapped = 0;//keeps track of how many letters user has mapped a value to
	public Game() {
	}
	
	
	public void onStartup() throws IOException {
		File file = new File("CryptogramSentences.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (true) {
			String next = br.readLine();
		//System.out.println(next);
			if (next == null) {
				break;
			} else {
				phrases.add(next);
			}
		}
		br.close();
	}
	
	/* 
	 * 	getter method for testing
	 * */
	public ArrayList<String> getPhrases() {
		return phrases;
	}
	
	//public for testing purposes
	public void establishCrypt(Cryptogram crypto) {
		crypt = crypto.getEncryptedArrayList();
		crypt2 = crypto.getEncryptedArrayList();
		answer = crypto.getPhraseArrayListStatic();
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
			String type = scan.next();
			if (type.toLowerCase().equals("letters")) {
				cryptogram = createLetters();
				decided = true;
			} else if(type.toLowerCase().equals("numbers")) {
				cryptogram = createNumbers();
				decided = true;
			} else {
				System.out.println("Sorry that doesn't appear to be an option, would you like numbers or letters?");
			}
		}
		establishCrypt(cryptogram);
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
		boolean guessed = false;//checks if guess has been made or value has been replaced
		boolean correct = false;//checks if whole cryptogram is correct
		Scanner myObj = new Scanner(System.in);
		System.out.println("Please enter your guess: ");
		String guess = myObj.nextLine();//this is the guess
		if((guesses.isEmpty()==false)&&(guesses.contains(guess))){//checks if letter has been guessed before
			guessed = true;
		}
		if(guessed == true) {
			System.out.println("You have already guessed this letter. Please try again.");//error message if letter has been guessed
			checkPrint = true;
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
			for(int i = 0; i < crypt2.size();i++) {
				if(crypt2.get(i).equals(value)) {
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
				for(int i = 0; i<crypt2.size();i++) {
					if(crypt2.get(i).equals(value)) {
						crypt.remove(i);
						crypt.add(i, guess);
					}
				}
				guesses.add(guess);
				mapped++;
				//check if guess was correct?
				if(crypto.getLetter(value).equals(guess)) {
					player.addCorrectGuesses();
				}
				player.addTotalGuesses();
			}else {//what happens if this not the first time the value is being mapped
				for(int i = 0; i<crypt2.size();i++) {
					if(crypt2.get(i).equals(crypt.get(valuePlaces.get(guessedAt)))) {//this compares what is in crypt to 
						crypt.remove(i);
						crypt.add(i, guess);
					}
				}
				guesses.add(guess);//add guess
				if(crypto.getLetter(value).equals(guess)) {
					player.addCorrectGuesses();
				}
				player.addTotalGuesses();
			}
			if(mapped == (crypt2.size()/2)) {
				player.incrementCryptogramsPlayed();
				for(int i = 0; i <(answer.size())/2;i++) {
					if(crypt.get(i).equals(answer.get(i))) {
						correct = true;
					}else {
						correct = false;
						break;
					}
				}
				if(correct == false) {
				System.out.println("User has failed cryptogram :(");
				}else {
					System.out.println("User has successfully completed cryptogram!!");
					player.addSolved();
				}
			}

		}
		//myObj.close();
	}
	
	public void undoLetter() {    
		
		int guessedAt = 0;
		

		Scanner myObj = new Scanner(System.in);
		System.out.println("Please enter a letter from the cryptogram to remove its mapped value");
		String letter = myObj.nextLine();//this is the letter that has a mapped value
		boolean guessed = false;
		// finding the position of the guessed letter
		for(int i = 0; i < values.size(); i++) {
			if(values.get(i).equals(letter)) {
				guessedAt = i; 
				guessed = true;
			}
				// if not found prints error message
		}
		if(!guessed) {
			System.out.println("Not been guessed");
			return;
		}
		
		
            // updating guessedAt to the position that the letter has been guessed
			guessedAt = valuePlaces.get(guessedAt);
			
		
		//checking if crypt letter has been guessed
		for(int i = 0; i < crypt.size();i++) {
			if(crypt.get(i).equals(crypt.get(guessedAt))){
				
				crypt.set(i, letter); // setting back letter to its original state
				System.out.println("The mapped letter has been cleared");
			}
		}
		//myObj.close();
	}


}
