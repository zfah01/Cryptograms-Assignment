import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;


public class Driver {
	static String saveFileName = "save.json";

	public static void main(String[] args) {
		Scanner myScan = new Scanner(System.in);
		System.out.println("Welcome to Cryptogram!!");

		String username = "";
		do {
			System.out.println("Enter your username: ");
			username = myScan.nextLine().trim().toLowerCase();
		} while (username.trim().isEmpty());

		Players playerGameMapping = new Players("playerFile.txt");
		playerGameMapping.loadPlayers();

		Player player = loadPlayer(username, playerGameMapping);

		Game game = null;
		System.out.println("Do you want to load a previously saved game? [y] or [n]");
		if (myScan.nextLine().toLowerCase().trim().equals("y"))
			game = loadGame(player);  // load game for current player
		if (game == null) {  // no game loaded or player doesnt want to load
			System.out.println("Creating new game ...");
			game = new Game();
			game.decideCryptogram(myScan);
		}


		game.printEncryption();
		String response;
		while (true) {
			System.out.println("What would you like to do? enter the number");
			System.out.println("(1) Make Guess    (2) Remove Guess     (3) Give Up      (4) Get Hint    (5) See my Stats	(6) Sava Game	(7) Show Scoreboard    (8) Leave");
			response = myScan.nextLine().trim();
			if(response.equals("1")) { //make guess
				game.enterLetter(player);
				
			} else if(response.equals("2")) { //remove guess
				game.undoLetter();
				
			} else if(response.equals("3")) { //give up and choose a new cryptogram 
				game.giveUp();
				game.decideCryptogram(myScan);
				game.printEncryption();
				
			} else if (response.equals("4")){ 
		        System.out.println("Please enter the value to get hint for: ");
		        String value = myScan.nextLine().trim().toUpperCase();
		        game.getHint(value);
		        
			} else if(response.equals("5")) { //print the stats of the current player
				player.printPlayerStats();

			} else if(response.equals("6")) { //saves the current game
				game.saveGame(player);

			} else if(response.equals("7")){ //prints the scoreboard
				game.printScoreBoard(playerGameMapping);
				
			} else if(response.equals("8")) { //exits the game
				System.out.print("Do you want to save your profile? [y] or [n]");
				response = myScan.nextLine().trim();
				playerGameMapping.savePlayer(response, player.getUsername());
				break;
				
			} else { //if they don't give a valid answer
				System.out.println("I'm sorry that doesn't seem to be a valid input, please try again");
				break;
			}
		}
	}
	

	private static Game loadGame(Player player){
		Gson gson = new Gson();
		int chosenGameIndex;
		List<JsonObject> savedData = null;

		try {
			// read save file and load contents into list of json
			JsonReader jReader = new JsonReader(new FileReader(saveFileName));
			savedData = gson.fromJson(jReader,  new TypeToken<List<JsonObject>>(){}.getType());
		}
		catch (FileNotFoundException ex) {}
		catch (JsonSyntaxException ex) {  // if save file was found to be corrupt, take backup and abort load
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println("ERROR: save file is corrupt. File will be backed up and new game will be created");
			String newFileName = "save_" + dtf.format(now) + ".json";
			try {
				Files.move(Paths.get(saveFileName), Paths.get(newFileName));
			} catch (IOException ioex) {
				System.out.println("ERROR: Could not rename corrupt save file");
				ioex.printStackTrace();
			}
		}
		if (savedData == null) {
			System.out.println("ERROR: No saved games found");
			return null;
		}
		List<JsonObject> currentPlayerGames = new ArrayList<>();

		for (JsonObject g: savedData){  // filter loaded games by player name
			if (g.get("player").getAsString().trim().equals(player.getUsername()))
				currentPlayerGames.add(g);
		}
		if (currentPlayerGames.isEmpty()){
			System.out.println("ERROR: No saved games found for you");
			return null;
		}

		chosenGameIndex = getChosenGameToLoad(currentPlayerGames);
		if (chosenGameIndex == -1) return null; // cancel load and create new game
		Game loadedGame = createLoadedGame(currentPlayerGames.get(chosenGameIndex));
		deleteLoadedGameFromSaveFile(chosenGameIndex);
		return loadedGame;
	}

	private static int getChosenGameToLoad(List<JsonObject> currentPlayerGames){
		int chosenGameIndex;
		Gson gson = new Gson();
		if (currentPlayerGames.size() == 1){  // if there is only one saved game, load it with no need to ask player
			chosenGameIndex = 0;
		} else {
			System.out.println("Choose a game to load: ");
			System.out.println("0 - Go back and create a new game");
			for (int i = 1; i <= currentPlayerGames.size(); i++) {
				JsonObject g = currentPlayerGames.get(i - 1);
				Cryptogram c = gson.fromJson(g.get("cryptogram").getAsString(), Cryptogram.class);
				String encryptedPhrase = c.encrypted;
				String type = c.type;
				String gameDate = g.get("time").getAsString();
				System.out.println(i + " - " + type + " - " + encryptedPhrase + " - " + gameDate);
			}
			Scanner myScan = new Scanner(System.in);
			int chosenGame = myScan.nextInt();
			while (chosenGame < 0 || chosenGame > currentPlayerGames.size()) {
				System.out.println("ERROR: Chosen game is not within the list. Please choose again");
				chosenGame = myScan.nextInt();
			}
			chosenGameIndex = chosenGame - 1;
		}
		return chosenGameIndex;
	}

	private static void deleteLoadedGameFromSaveFile(int chosenGameIndex){
		List <JsonObject> savedData = null;
		Gson gson = new Gson();
		try {
			JsonReader jReader = new JsonReader(new FileReader(saveFileName));
			savedData = gson.fromJson(jReader,  new TypeToken<List<JsonObject>>(){}.getType());
		} catch (FileNotFoundException ex) {

		}
		if (savedData == null) {
			System.out.print("ERROR: could not remove loaded game from save file");
			return;
		}
		savedData.remove(chosenGameIndex);

		try {
			Writer writer = new FileWriter(saveFileName, false);
			new Gson().toJson(savedData, writer);
			writer.close();
			System.out.println("INFO: Loaded game has been removed from save file. Please save game again if needed");
		} catch (IOException ex) {
			System.out.println("ERROR: could not update save file");
			ex.printStackTrace();
		}
	}

	public static Game createLoadedGame(JsonObject g){
		Gson gson = new Gson();
		String cType = gson.fromJson(g.get("cryptogram").getAsString(), Cryptogram.class).type;
		Cryptogram cryptogram;

		// load back from json into java objects
		if (cType.equals("letters")) {
			cryptogram = gson.fromJson(g.get("cryptogram").getAsString(), LettersCryptogram.class);
		} else {
			cryptogram = gson.fromJson(g.get("cryptogram").getAsString(), NumbersCryptogram.class);
		}
		ArrayList<String> guesses = gson.fromJson(g.get("guesses").getAsString(),  new TypeToken<List<String>>(){}.getType());
		ArrayList<String> crypt = gson.fromJson(g.get("crypt").getAsString(),  new TypeToken<List<String>>(){}.getType());
		ArrayList<String> crypt2 = gson.fromJson(g.get("crypt2").getAsString(),  new TypeToken<List<String>>(){}.getType());
		ArrayList<String> values = gson.fromJson(g.get("values").getAsString(),  new TypeToken<List<String>>(){}.getType());
		ArrayList<Integer> valuePlaces = gson.fromJson(g.get("valuePlaces").getAsString(),  new TypeToken<List<Integer>>(){}.getType());
		ArrayList<String> answer = gson.fromJson(g.get("answer").getAsString(),  new TypeToken<List<String>>(){}.getType());
		int mapped = g.get("mapped").getAsInt();

		String encryptedPhrase = cryptogram.encrypted;
		String gameDate = g.get("time").getAsString();

		Game loadedGame =  new Game(guesses, crypt, crypt2, values, valuePlaces, answer, cryptogram, mapped);
		System.out.println("Game loaded: " + cType + " - " + encryptedPhrase + " - " + gameDate);
		return loadedGame;
	}

	public static Player loadPlayer(String username, Players playerGameMapping) {
		Player currentPlayer = playerGameMapping.findPlayer(username);
		if (currentPlayer == null) {
			System.out.println("This player does not exist!");
			System.out.println("New Player has been created: " + username);
			currentPlayer = new Player(username);
			playerGameMapping.addPlayer(currentPlayer);
		}
		return currentPlayer;

	}

}


