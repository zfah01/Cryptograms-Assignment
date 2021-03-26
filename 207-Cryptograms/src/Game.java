import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

public class Game {
	public ArrayList<String> guesses = new ArrayList<>();//array with letters guessed
	public ArrayList<String> crypt;//array with cryptogram and guesses
	public ArrayList<String> crypt2;//hold unchanged encrypted cryptogram
	public ArrayList<String> values = new ArrayList<String>();//holds the values the guess replaces onlywhen replaced
	public ArrayList<Integer> valuePlaces = new ArrayList<Integer>();//keeps track of where the values were before being replaced
	public ArrayList<String> answer;//will be changed to hold answer
	private Cryptogram cryptogram; //only used in the decide cryptogram function to avoid an error
	private ArrayList<String> phrases = new ArrayList<>();
	public boolean checkPrint = false;//used for testing purposes
	public int mapped = 0;//keeps track of how many letters user has mapped a value to
	private File playerFile;
	String saveFileName = "save.json";
	private Player currentPlayer;



	public Game() {

		playerFile = new File("playerFile.txt");
		try {
			onStartup();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Error with reading the file");
			exit(1);
		}
	}

	public Game(ArrayList<String> guesses, ArrayList<String> crypt, ArrayList<String> crypt2, ArrayList<String> values, ArrayList<Integer> valuePlaces, ArrayList<String> answer, Cryptogram cryptogram, int mapped) {
		// constructor to load game
		this();
		this.guesses = guesses;
		this.crypt = crypt;
		this.crypt2 = crypt2;
		this.values = values;
		this.valuePlaces = valuePlaces;
		this.answer = answer;
		this.cryptogram = cryptogram;
		this.mapped = mapped;
	}


	public File getPlayerFile() {
		return playerFile;
	}
	
	public void onStartup() throws IOException {
		File file = new File("CryptogramSentences.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (String line = br.readLine(); line != null; line = br.readLine()){
			phrases.add(line);
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
		crypt2 = new ArrayList<>(crypt);
		answer = crypto.getPhraseArrayList();
	}
	//helper method to create the cryptogram
	private Cryptogram createLetters() {
		Cryptogram crypto = new LettersCryptogram(phrases.get(ThreadLocalRandom.current().nextInt(1, 10)));
		return crypto;
	}
	
	//helper method to create the cryptogram
	private Cryptogram createNumbers() {
		Cryptogram crypto = new NumbersCryptogram(phrases.get(ThreadLocalRandom.current().nextInt(1, 14)));
		return crypto;
	}


	
	public void decideCryptogram(Scanner scan) {

		while(true) {
			System.out.println("Would you like a [numbers] or [letters] cryptogram: ");
			String type = scan.nextLine().trim();
			if (type.equalsIgnoreCase("letters")) {

				cryptogram = createLetters();
				break;
			} else if(type.equalsIgnoreCase("numbers")) {
				cryptogram = createNumbers();
				break;
			} else {
				System.out.println("Sorry that doesn't appear to be an option, would you like [numbers] or [letters]? ");
			}
		}
		establishCrypt(cryptogram);
	}
	
	public void printEncryption() {
		cryptogram.printEncryption();
	}
	
	public void enterLetter(Player player) {
		boolean guessed = false;//checks if guess has been made or value has been replaced
		boolean correct = false;
		Scanner myObj = new Scanner(System.in);
		System.out.println("Please enter your guess: ");
		String guess = myObj.nextLine().trim().toUpperCase();  //this is the guess
		if(!guesses.isEmpty() && guesses.contains(guess)){  //checks if letter has been guessed before
			guessed = true;
		}
		if (guessed) {
			System.out.println("You have already guessed this letter. Please try again.");//error message if letter has been guessed
			checkPrint = true;
		}else {

			System.out.println("Please enter the value to replace your guess with: ");
			String value = myObj.nextLine().trim().toUpperCase();// get value to replace with guess
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
			if(guessed) {//what happens if value has been guessed before
				System.out.println("You have already replaced this value. Do you want to overwrite this? y/n");
				String yesno = myObj.nextLine().trim().toLowerCase();
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
			if (!valueThere && !guessed) {
				System.out.println("ERROR: value selected not in cryptogram");
				return;
			}
			if (!guessed)	{//what happens if this is the first time the value is being mapped
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
				if(answer.get(guessedAt).equals(guess)) {
					player.addCorrectGuesses();
				}
				player.addTotalGuesses();
				player.updateAccuracy();
			}else {//what happens if this not the first time the value is being mapped
				for(int i = 0; i<crypt2.size();i++) {
					if(crypt2.get(i).equals(crypt.get(valuePlaces.get(guessedAt)))) {//this compares what is in crypt to 
						crypt.remove(i);
						crypt.add(i, guess);
					}
				}
				guesses.add(guess);//add guess
				if(answer.get(guessedAt).equals(guess)) {
					player.addCorrectGuesses();
				}
				player.addTotalGuesses();
				player.updateAccuracy();
			}
			System.out.println("You have replaced " + value + " with " + guess);
			for(int i = 0; i < crypt.size()/2; i++) {
				System.out.print(crypt.get(i));
			}
			System.out.println();
			checkMapped(player, myObj);
		}
		//myObj.close();
	}
	public void checkMapped(Player player, Scanner myObj) {//helper method for enter letter to see if user has mapped all values
		boolean correct = false;//checks if whole cryptogram is correct
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
			if(!correct) {
			System.out.println("User has failed cryptogram :(");
			}else {
				System.out.println("User has successfully completed cryptogram!!");
				player.addSolved();
				decideCryptogram(myObj);
			}
		}
		
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
	
	public void giveUp() {
		System.out.println("I'm sorry you've given up");
		System.out.println("The answer to your cryptogram is: " + cryptogram.getPhrase());
	}

	public void saveGame(Player player) {
		JsonObject gameJson = getGameAsJsonObject(player, LocalDateTime.now());
		List <JsonObject> savedData = getSavedDataFromFile(LocalDateTime.now());
		deleteSameSavedGameFromSavedDataList(savedData, player);
		savedData.add(gameJson);
		saveSavedGamesListToFile(savedData);
	}

	public JsonObject getGameAsJsonObject(Player player, LocalDateTime now){
		Gson gson = new Gson();
		JsonObject gameJson = new JsonObject();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		gameJson.addProperty("guesses", gson.toJson(guesses));
		gameJson.addProperty("crypt", gson.toJson(crypt));
		gameJson.addProperty("crypt2", gson.toJson(crypt2));
		gameJson.addProperty("values", gson.toJson(values));
		gameJson.addProperty("valuePlaces", gson.toJson(valuePlaces));
		gameJson.addProperty("cryptogram", gson.toJson(cryptogram));
		gameJson.addProperty("answer", gson.toJson(answer));
		gameJson.addProperty("checkPrint", checkPrint);
		gameJson.addProperty("mapped", mapped);
		gameJson.addProperty("player", player.getUsername());
		gameJson.addProperty("time", dtf.format(now));

		return gameJson;

	}

	public List<JsonObject> getSavedDataFromFile(LocalDateTime now){
		Gson gson = new Gson();
		List <JsonObject> savedData;
		try {
			JsonReader jReader = new JsonReader(new FileReader(saveFileName));
			savedData = gson.fromJson(jReader,  new TypeToken<List<JsonObject>>(){}.getType());
			if (savedData == null) savedData = new ArrayList<JsonObject>();
		} catch (FileNotFoundException ex) {
			savedData = new ArrayList<JsonObject>();
		} catch (JsonSyntaxException ex) {  // if save file was found to be corrupt, take backup and create a new one
			System.out.println("ERROR: save file is corrupt. File will be backed up and new save file will be created");
			DateTimeFormatter dtf_filename = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String newFileName = "save_" + dtf_filename.format(now) + ".json";
			try {
				Files.move(Paths.get(saveFileName), Paths.get(newFileName));
			} catch (IOException ioex) {
				System.out.println("ERROR: Could not rename corrupt save file");
				ioex.printStackTrace();
			}
			savedData = new ArrayList<JsonObject>();
		}
		return savedData;
	}

	public void deleteSameSavedGameFromSavedDataList(List<JsonObject> savedData, Player player){
		Gson gson = new Gson();
		// overwrite saved game when the exact same game is re-saved
		for (int i=0; i < savedData.size(); i++){
			JsonObject savedGame = savedData.get(i);
			if (savedGame.get("player").getAsString().equals(player.getUsername()) &&
					savedGame.get("crypt").getAsString().equals(gson.toJson(crypt))) {
				savedData.remove(i);
				System.out.println("Overwriting older copy of saved game");
				break;
			}
		}
	}

	public void saveSavedGamesListToFile(List<JsonObject> savedData){
		try {
			Writer writer = new FileWriter(saveFileName, false);
			new Gson().toJson(savedData, writer);
			System.out.println("Game saved");
			writer.close();
		} catch (IOException ex) {
			System.out.println("ERROR: could not save game");
			ex.printStackTrace();
		}
	}


	//this function is mostly used for testing purposes
	public Cryptogram getCryptogram() {
		return cryptogram;
	}
	
	public void printScoreBoard(Players playerMap) {
		ArrayList<Player> players = playerMap.getPlayers();
		
		//sorts them using bubble sort
	        int n = players.size();
	        for (int i = 0; i < n-1; i++)
	            for (int j = 0; j < n-i-1; j++)
	                if (players.get(j).getSolved() < players.get(j+1).getSolved())
	                {
	                    // swap arr[j+1] and arr[j]
	                    Player temp = players.get(j);
	                    players.set(j, players.get(j+1));//[j] = arr[j+1];
	                    players.set(j+1, temp);//arr[j+1] = temp;
	                }
		for(int i = 0; i < 10 && i < players.size(); i++) {
			System.out.println(i + ".-----" + players.get(i).getUsername());
		}
	}

	public void getHint(String value) {
        //Scanner myObj = new Scanner(System.in);

        boolean valueThere = false;

        int replaceAt = 0;
        for(int i = 0; i < crypt2.size()/2;i++) {
            if(crypt2.get(i).equals(value)) {
                valueThere = true;
                replaceAt = i;//need a value where the value definitely has been
            }
        }
        if(!valueThere) {
            System.out.println("ERROR: value selected not in cryptogram");
        }else {
            for(int i = 0; i < crypt2.size()/2;i++) {
                if(crypt2.get(i).equals(value)) {
                    crypt.set(i, answer.get(i));
                }
            }
            System.out.println("You have replaced "+ value +" with "+ answer.get(replaceAt));
            System.out.println("The new cryptogram is: ");
            for(int i = 0; i < crypt.size()/2;i++) {
                System.out.print(crypt.get(i));
            }
            System.out.println();
        }

    }
}
